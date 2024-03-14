package dev.silenzzz.rutracker.api;

import dev.silenzzz.rutracker.api.constant.PageURL;
import dev.silenzzz.rutracker.api.constant.SizeType;
import dev.silenzzz.rutracker.api.constant.XPath;
import dev.silenzzz.rutracker.api.domain.Category;
import dev.silenzzz.rutracker.api.domain.Country;
import dev.silenzzz.rutracker.api.domain.Post;
import dev.silenzzz.rutracker.api.domain.Topic;
import dev.silenzzz.rutracker.api.domain.Torrent;
import dev.silenzzz.rutracker.api.domain.User;
import dev.silenzzz.rutracker.api.exception.RuTrackerException;
import dev.silenzzz.rutracker.api.model.SearchConditions;
import dev.silenzzz.rutracker.api.model.SearchResult;
import dev.silenzzz.rutracker.api.value.AccountCredentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder


// TODO: GET RID OF IT NOSONAR


public class DefaultRuTrackerClient implements RuTrackerClient {

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-LLL-yy HH:mm", new Locale("ru", "RU"));
    private final SimpleDateFormat profileDateFormatter = new SimpleDateFormat("yyyy-LL-dd", new Locale("ru", "RU"));

    private Map<String, String> cookies;

    public DefaultRuTrackerClient(@NonNull AccountCredentials credentials) throws RuTrackerException {
        refreshCookies();
    }

    @Override
    @SneakyThrows
    public SearchResult search(SearchConditions searchConditions) {
        refreshCookies();
        Connection.Response response = Jsoup.connect(PageURL.SEARCH.getValue())
                .cookies(cookies)
                .data(Map.of("f[]", searchConditions.getCategories().stream().map(c -> String.valueOf(c.getId())).collect(Collectors.joining(",")),
                        "nm", searchConditions.getQuery()))
                .method(Connection.Method.POST)
                .execute();

        Document document = response.parse();

        Element root = document.selectXpath("//*[@id=\"tor-tbl\"]/tbody").first();
        
        //root.children().stream()
               // .

        return null;
    }

    @Override
    public Topic findTopicById(long id) {
        Document document = getDocument(PageURL.TOPIC.insertId(id));
        return Topic.builder()
                .id(id)
                .title(document.getElementById("topic-title").text())
                .category(getCategory(document))
                //.posts(getPosts(document)) FIXME NOSONAR
                .torrent(getTorrent(document))
                .build();
    }

    @Override
    @SneakyThrows
    public User findUserById(long id) {
        Document document = getDocument(String.format(PageURL.PROFILE.getValue(), id));
        Elements elements = document.getElementById("main_content").getAllElements();

        return User.builder()
                .id(id)
                .nickname(elements.first().getElementById("profile-uname").text())
                .country(extractCountry(elements.first()))
                .lastVisit(profileDateFormatter.parse(getElementByXPath(document, XPath.LAST_VISIT)
                        .attr("title")
                        .replace("Посл. визит: ", "")))
                .registered(profileDateFormatter.parse(getElementByXPath(document, XPath.REGISTERED).text()))
                .messagesCount(Long.parseLong(getElementByXPath(document, XPath.MESSAGES_COUNT).text()))
                .seniority(getElementByXPath(document, XPath.USER_SENIORITY).text())
                .build();
    }

    @Override
    public Torrent findTorrentFileById(long id) {
        return Torrent.builder()
                .id(id)
                .link(PageURL.TORRENT_DOWNLOAD.insertId(id))
                // FIXME: 11.11.2023 
                //.magnetLink(Url.TOREENT_DOWNLOAD_MAGNET.insertId(id))
                .build();
    }

    @Override
    public List<Category> getAllCategories() {
        Document document = getDocument(PageURL.CATEGORIES.getValue());
        Element root = document.selectXpath("//*[@id=\"fs-main\"]").first();

        List<Element> groups = root.children().stream()
                .filter(e -> e.is("optgroup"))
                .toList();

        return groups.stream()
                .flatMap(e -> e.children().stream())
                .filter(e -> e.is("option"))
                .map(this::mapToCategory)
                .toList();
    }

    private Category mapToCategory(Element e) {
        return Category.builder()
                .id(Long.parseLong(e.attr("value")))
                .parentName(extractParentName(e))
                .name(e.text().replace("|- ", ""))
                .build();
    }

    private String extractParentName(Element e) {
        return new StringBuilder(e.parent().attr("label")).deleteCharAt(0).toString();
    }

    private Element getElementByXPath(Document document, XPath xpath) {
        return document.selectXpath(xpath.getValue()).first();
    }

    private Torrent getTorrent(Document document) {
        long id = Long.parseLong(document.selectXpath(XPath.TORRENT_LINK.getValue())
                .first()
                .attr("href")
                .replace("dl.php?t=", ""));

        String[] raw = document.selectXpath(XPath.TORRENT_FILE_SIZE.getValue()).first().text().split(" ");

        return Torrent.builder()
                .id(id)
                .filesSize(Float.parseFloat(raw[0]))
                .fileSizeType(SizeType.valueOf(raw[1]))
                .link(PageURL.TORRENT_DOWNLOAD.insertId(id))
                .magnetLink(document.selectXpath(XPath.TORRENT_MAGNET_LINK.getValue()).first().attr("href"))
                .build();
    }

    private Category getCategory(Document document) {
        return Category.builder()
                .id(Long.parseLong(document.selectXpath(XPath.CATEGORY.getValue())
                        .first()
                        .attr("href")
                        .replace("viewforum.php?f=", "")))
                .name(document.selectXpath(XPath.CATEGORY.getValue()).first().text())
                .build();
    }

    private List<Post> getPosts(Document document) {
        return document.selectXpath("//tbody[@class='row1' or @class='row2']").stream()
                .map(this::extractPost)
                .toList();
    }

    @SneakyThrows
    private Post extractPost(Element e) {
        Element authorElement = e.getElementsByClass("poster_info td1 hide-for-print").first();
        Element postElement = e.getElementsByClass("message td2").first();

        return Post.builder()
                //.date(extractDate(e))
                .user(User.builder()
                        .id(extractId(e))
                        .nickname(extractNickname(e))
                        .messagesCount(extractMessageId(authorElement))
                        .country(extractCountry(e))
                        .build())
//                .bodyText(postElement.getElementsByClass("post_body").first().text())
//                .bodyHtml(postElement.getElementsByClass("post_body").first().html())
                .build();
    }

    private Country extractCountry(Element e) {
        Element element = e.getElementsByClass("poster-flag").first();

//        if (element == null) {
//            return new Country();
//        }

        String title = element.attr("title");
        String src = element.attr("src");

        return new Country(title, src);
    }

    private long extractMessageId(Element e) {
        return Long.parseLong(e.getElementsByClass("posts").first().text().replace("Сообщений: ", ""));
    }

    @SneakyThrows
    private void refreshCookies() {
//        Connection.Response response = Jsoup.connect(PageURL.LOGIN.getValue())
//                .followRedirects(false)
//                .proxy(proxy)
//                .data(Map.of("login_username", credentials.getUsername(),
//                        "login_password", credentials.getPassword(),
//                        "login", "Enter"))
//                .method(Connection.Method.POST)
//                .execute();

//        if (response.statusCode() != 302 && response.statusCode() != 200) {
//            throw new CredentialException(response.statusMessage());
//        }
//
//        cookies = response.cookies();
    }

    private long extractId(Element e) {
        return Long.parseLong(e.getElementsByClass("txtb")
                .stream()
                .filter(element -> element.text().equals("[Профиль]"))
                .map(element -> element.attr("href"))
                .map(s -> s.substring(31))
                .findFirst().orElseThrow());
    }

    private String extractNickname(Element e) {
        Elements elementsByClass = e.getElementsByClass("poster_info td1 hide-for-print");
        String nickname = elementsByClass.first().getElementsByClass("nick ").text();

        if (nickname.isEmpty()) {
            return elementsByClass.first().getElementsByClass("nick nick-author").text();
        }

        return nickname;
    }

    @SneakyThrows
    private Date extractDate(Element e) {
        String text = e.getElementsByClass("p-link small").first().text();
        if (text.contains("Май")) {
            text = new StringBuilder(text).insert(6, ".").toString();
        }

        return dateFormatter.parse(text);
    }

    @SneakyThrows
    private Document getDocument(String url) {
        refreshCookies();

//        return Jsoup.connect(url)
//                .proxy(proxy)
//                .cookies(cookies)
//                .get();

        return null;
    }
}

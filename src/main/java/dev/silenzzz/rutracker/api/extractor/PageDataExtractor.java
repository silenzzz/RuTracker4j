package dev.silenzzz.rutracker.api.extractor;

import dev.silenzzz.rutracker.api.constant.PageURL;
import dev.silenzzz.rutracker.api.constant.Query;
import dev.silenzzz.rutracker.api.constant.SizeType;
import dev.silenzzz.rutracker.api.domain.Category;
import dev.silenzzz.rutracker.api.domain.Country;
import dev.silenzzz.rutracker.api.domain.Post;
import dev.silenzzz.rutracker.api.domain.Topic;
import dev.silenzzz.rutracker.api.domain.Torrent;
import dev.silenzzz.rutracker.api.domain.User;
import dev.silenzzz.rutracker.api.exception.RuTrackerConnectionException;
import dev.silenzzz.rutracker.api.exception.RuTrackerException;
import dev.silenzzz.rutracker.api.exception.RuTrackerParseException;
import dev.silenzzz.rutracker.api.exception.RuTrackerTopicNotFoundException;
import dev.silenzzz.rutracker.api.net.JSoupHttpClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class PageDataExtractor { // TODO: interface? NOSONAR

    private final JSoupHttpClient client;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-LLL-yy HH:mm", new Locale("ru", "RU"));

    public PageDataExtractor(JSoupHttpClient client) throws RuTrackerConnectionException {
        this.client = client;

        if (client.getCookies().isEmpty()) {
            throw new RuTrackerConnectionException();
        }
    }

    public Topic findTopicById(long id) throws RuTrackerException {
        Document document = client.fetch(PageURL.TOPIC.insertId(id));

        if (document.select(".mrg_16").first() != null) {
            throw new RuTrackerTopicNotFoundException(String.format("Topic with given id=%d", id));
        }

        try {
            return Topic.builder()
                    .id(id)
                    .title(document.getElementById("topic-title").text())
                    .category(getCategory(document))
                    //.posts(getPosts(document)) FIXME NOSONAR
                    .torrent(getTorrent(document))
                    .build();
        } catch (NullPointerException e) {
            throw new RuTrackerParseException(String.format("An error occurred while parsing the topic with id=%d", id), e); // TODO: Allow null for certain fields NOSONAR
        }
    }

    private Category getCategory(Document document) {
        return Category.builder()
                .id(Long.parseLong(document.select(Query.CATEGORY.getValue())
                        .get(1)
                        .attr("href")
                        .replace("viewforum.php?f=", "")))
                .name(document.select(Query.CATEGORY.getValue()).get(1).text())
                .build();
    }

    private List<Post> getPosts(Document document) {
        return document.selectXpath("//tbody[@class='row1' or @class='row2']").stream()
                .map(this::extractPost)
                .toList();
    }

    // FIXME NOSONAR
    private Post extractPost(Element e) {
        Element authorElement = e.getElementsByClass("poster_info td1 hide-for-print").first();
        Element postElement = e.getElementsByClass("message td2").first();

        return Post.builder()
                //.date(extractDate(e)) // FIXME NOSONAR
                .user(User.builder()
                        .id(extractId(e))
                        .nickname(extractNickname(e))
                        .messagesCount(extractMessageId(authorElement))
                        //.country(extractCountry(e))
                        .build())
//                .bodyText(postElement.getElementsByClass("post_body").first().text())
//                .bodyHtml(postElement.getElementsByClass("post_body").first().html())
                .build();
    }

    private long extractId(Element e) {
        return Long.parseLong(e.getElementsByClass("txtb")
                .stream()
                .filter(element -> element.text().equals("[Профиль]"))
                .map(element -> element.attr("href"))
                .map(s -> s.substring(31))
                .findFirst().orElseThrow());
    }

    private long extractMessageId(Element e) {
        return Long.parseLong(e.getElementsByClass("posts").first().text().replace("Сообщений: ", ""));
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

    private String extractNickname(Element e) {
        Elements elementsByClass = e.getElementsByClass("poster_info td1 hide-for-print");
        String nickname = elementsByClass.first().getElementsByClass("nick ").text();

        if (nickname.isEmpty()) {
            return elementsByClass.first().getElementsByClass("nick nick-author").text();
        }

        return nickname;
    }

    private Torrent getTorrent(Document document) {
        long id = Long.parseLong(document.select(Query.TORRENT_FILE_DOWNLOAD_LINK.getValue())
                .first()
                .attr("href")
                .replace("dl.php?t=", ""));

        String[] rawSize = document.select(Query.FILE_SIZE.getValue()).first().text().split(" ");

        return Torrent.builder()
                .id(id)
                .filesSize(Float.parseFloat(rawSize[0]))
                .fileSizeType(SizeType.valueOf(rawSize[1]))
                .link(PageURL.TORRENT_DOWNLOAD.insertId(id))
                // .magnetLink(document.selectXpath(XPath.TORRENT_MAGNET_LINK.getValue()).first().attr("href")) FIXME NOSONAR
                .build();
    }

    private Date extractDate(Element e) throws ParseException {
        String text = e.getElementsByClass("p-link small").first().text();
        if (text.contains("Май")) {
            text = new StringBuilder(text).insert(6, ".").toString();
        }

        return dateFormatter.parse(text);
    }

    private Document getDocument(String url) throws RuTrackerConnectionException {
        return client.fetch(url);
    }
}

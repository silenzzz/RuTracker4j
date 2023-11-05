package dev.demmage.rutracker.api;

import dev.demmage.rutracker.api.constant.Url;
import dev.demmage.rutracker.api.domain.Torrent;
import dev.demmage.rutracker.api.domain.User;
import dev.demmage.rutracker.api.domain.Post;
import dev.demmage.rutracker.api.domain.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.security.auth.login.CredentialException;
import java.text.SimpleDateFormat;
import java.util.*;

@AllArgsConstructor
@Builder
public class DefaultRutrackerClient implements RutrackerClient {

    private final AccountCredentials credentials;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-LLL-yy HH:mm", new Locale("ru", "RU"));

    private Map<String, String> cookies;

    public DefaultRutrackerClient(@NonNull AccountCredentials credentials) {
        this.credentials = credentials;
        refreshCookies();
    }

    @Override
    public Topic findTopicById(@NonNull long id) {
        Document document = getDocument(String.format(Url.TOPIC_URL.getValue(), id));

        return Topic.builder()
                .id(id)
                .title(document.getElementById("topic-title").text())
                .posts(getPosts(document))
                .build();
    }

    @Override
    public User findUserById(@NonNull long id) {
        Document document = getDocument(String.format(Url.USER_URL.getValue(), id));
        Elements elementsByClass = document.getElementsByClass("user_details borderless w100");

//        elementsByClass.stream()
//                .map(e -> {
//
//                })
//
//        Element authorElement = e.getElementsByClass("poster_info td1 hide-for-print").first();
//        Element postElement = e.getElementsByClass("message td2").first();

        return null;
    }

    @Override
    public Torrent findTorrentFileById(long id) {
        return null;
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
                .date(extractDate(e))
                .user(User.builder()
                        .id(extractId(e))
                        .nickname(e.getElementsByClass("nick ").first().text())
                        .messagesCount(extractMessageId(authorElement))
                        //.country(Optional.of(authorElement.getElementsByClass("poster-flag").first().attr("title")).orElse("undefined"))
                        .build())
                .bodyText(postElement.getElementsByClass("post_body").first().text())
                .bodyHtml(postElement.getElementsByClass("post_body").first().html())
                .build();
    }

    private long extractMessageId(Element e) {
        return Long.parseLong(e.getElementsByClass("posts").first().text().replace("Сообщений: ", ""));
    }

    @SneakyThrows
    private void refreshCookies() {
        Connection.Response response = Jsoup.connect(Url.LOGIN_URL.getValue())
                .followRedirects(false)
                .data(Map.of("login_username", credentials.getUsername(),
                        "login_password", credentials.getPassword(),
                        "login", "Enter"))
                .method(Connection.Method.POST)
                .execute();

        if (response.statusCode() != 302 && response.statusCode() != 200) {
            throw new CredentialException(response.statusMessage());
        }

        cookies = response.cookies();
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
//        String nickname =
//
//        if (nickname.isEmpty()) {
//            return e.getElementsByClass("nick nick-author").first().text();
//        }

        return null;
    }

    @SneakyThrows
    private Date extractDate(Element e) {
        String text = e.getElementsByClass("p-link small").first().text();
        if (!text.contains("Май")) {
            text = new StringBuilder(text).insert(6, ".").toString();
        }

        return dateFormatter.parse(text);
    }

    @SneakyThrows
    private Document getDocument(String url) {
        refreshCookies();

        return Jsoup.connect(url)
                .cookies(cookies)
                .get();
    }
}

package dev.silenzzz.rutracker4j.scrapper.net;

import com.google.common.collect.ImmutableMap;
import dev.silenzzz.rutracker4j.scrapper.constant.URL;
import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;
import dev.silenzzz.rutracker4j.scrapper.parse.PageParser;
import dev.silenzzz.rutracker4j.scrapper.net.exception.AuthenticationException;
import dev.silenzzz.rutracker4j.scrapper.net.exception.ConnectionException;
import dev.silenzzz.rutracker4j.scrapper.net.exception.IncorrectCaptchaException;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class JSoupHttpClient {

    private static final Map<ErrorMessage, RuTracker4jException> messageToException = ImmutableMap.of( // NOSONAR
            ErrorMessage.INVALID_LOGIN_OR_PASSWORD, new AuthenticationException(ErrorMessage.INVALID_LOGIN_OR_PASSWORD.getEnValue()),
            ErrorMessage.CAPTCHA_TRY_AGAIN, new IncorrectCaptchaException(ErrorMessage.CAPTCHA_TRY_AGAIN.getEnValue())
    );

    private static final Map<String, String> headers = ImmutableMap.of( // TODO ofEntries NOSONAR
            "User-Agent", "Mozilla/5.0",
            "Accept-Charset", "utf-8"
    );

    private static final Cookies cookieCache = new Cookies();

    private final AccountCredentials credentials;
    private final Proxy proxy;

    public JSoupHttpClient(AccountCredentials credentials, @Nullable Proxy proxy) throws RuTracker4jException {
        this.credentials = credentials;
        this.proxy = proxy;

        authenticate();
    }

    public Document fetch(String url) throws ConnectionException {
        return fetch(url, Method.GET, Collections.emptyMap());
    }

    public Document fetch(String url, Method method, Map<String, String> payload) throws ConnectionException {
        try {
            return call(url, method, payload).parse();
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    private Response call(String url, Method method, Map<String, String> payload) throws IOException {
        Response response = Jsoup.connect(url)
                .headers(headers)
                .proxy(proxy)
                .cookies(cookieCache.get())
                .method(method)
                .data(payload)
                .execute();

        if (response.statusCode() != 200 && response.statusCode() != 404) { // He always returns 200 if authenticated
            throw new ConnectionException();
        }

        return response;
    }

    private void authenticate() throws RuTracker4jException {
        final String username = credentials.getUsername();
        final String password = credentials.getPassword();

        Response response;
        Document document;

        try {
            response = call(
                    URL.LOGIN.getValue(),
                    Method.POST,
                    Map.of("login_username", username,
                            "login_password", password,
                            "login", "Enter")
            );

            String url = Optional.of(response.url().toString()).orElseThrow(() ->
                    new ConnectionException(String.format("Invalid url: %s", response.url())));

            if (!url.equals(URL.INDEX.getValue()) && !url.equals(URL.LOGIN.getValue())) {
                throw new ConnectionException(String.format("Invalid redirect: %s", url));
            }

            Map<String, String> cookies = response.cookies();
            document = response.parse();

            if (cookies.isEmpty() && cookieCache.isEmpty()) {
                String errorMessage = PageParser.getErrorMessage(document);

                ErrorMessage message = Arrays.stream(ErrorMessage.values())
                        .filter(e -> e.getRuValue().equals(errorMessage))
                        .findFirst()
                        .orElseThrow(AuthenticationException::new);

                throw messageToException.get(message);
            }
            cookieCache.update(cookies);
        } catch (RuTracker4jException e) {
            throw e;
        } catch (NullPointerException | IOException e) {
            throw new AuthenticationException(e);
        }
    }

    public Map<String, String> refreshCookies() throws RuTracker4jException {
        authenticate();
        return getCookies();
    }

    public Map<String, String> getCookies() {
        return cookieCache.get();
    }
}

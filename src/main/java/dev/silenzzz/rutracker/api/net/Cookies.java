package dev.silenzzz.rutracker.api.net;

import java.util.HashMap;
import java.util.Map;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class Cookies {

    private final Map<String, String> values = new HashMap<>();

    public Cookies() {
    }

    public Cookies(Map<String, String> cookies) {
        this.values.putAll(cookies);
    }

    public Map<String, String> update(Map<String, String> cookies) {
        this.values.putAll(cookies);
        return new HashMap<>(cookies);
    }

    public Map<String, String> get() {
        return new HashMap<>(values);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}

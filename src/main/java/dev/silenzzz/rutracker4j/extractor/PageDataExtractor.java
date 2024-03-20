package dev.silenzzz.rutracker4j.extractor;

import dev.silenzzz.rutracker4j.constant.Query;
import dev.silenzzz.rutracker4j.constant.SizeType;
import dev.silenzzz.rutracker4j.constant.URL;
import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.exception.RuTrackerConnectionException;
import dev.silenzzz.rutracker4j.exception.RuTrackerException;
import dev.silenzzz.rutracker4j.exception.RuTrackerParseException;
import dev.silenzzz.rutracker4j.exception.RuTrackerTopicNotFoundException;
import dev.silenzzz.rutracker4j.net.JSoupHttpClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class PageDataExtractor {

    private final JSoupHttpClient client;

    private final Map<Long, Category> categoryCache;

    public PageDataExtractor(JSoupHttpClient client) throws RuTrackerException {
        this.client = client;

        if (client.getCookies().isEmpty()) {
            throw new RuTrackerConnectionException();
        }

        try {
            Document document = client.fetch(URL.CATEGORIES.getValue());

            HashMap<Long, Category> categories = document.select(Query.CATEGORY_OPTION.getValue()).stream()
                    .map(e -> {
                        long id = Long.parseLong(e.attr("value"));

                        Category category = Category.builder()
                                .id(id)
                                .name(e.text())
                                .build();

                        return new AbstractMap.SimpleEntry<>(id, category);
                    })
                    .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);

            if (categories.isEmpty()) {
                throw new RuTrackerParseException();
            }

            categoryCache = categories;

        } catch (RuTrackerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTrackerException(e);
        }
    }

    public Topic findTopicById(long id) throws RuTrackerException {
        try {
            Document document = client.fetch(URL.TOPIC.insertId(id));

            if (document.select(Query.TOPIC_NOT_FOUND.getValue()).first() != null) {
                throw new RuTrackerTopicNotFoundException(String.format("Topic with given id=%d not found", id));
            }

            //noinspection DataFlowIssue
            return Topic.builder()
                    .id(id)
                    .title(document.select(Query.TOPIC_TITLE.getValue()).first().text())
                    .categories(getCategories(document))
                    .attach(getAttach(id, document))
                    .build();
        } catch (RuTrackerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTrackerException(e);
        }
    }

    public Attach findAttachById(long id) throws RuTrackerException {
        try {
            return getAttach(id, client.fetch(URL.TOPIC.insertId(id)));
        } catch (RuTrackerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTrackerException();
        }
    }

    public Category findCategoryById(long id) throws RuTrackerParseException {
        return Optional.ofNullable(categoryCache.get(id)).orElseThrow(RuTrackerParseException::new);
    }

    public Collection<Category> getAllCategories() throws RuTrackerParseException {
        return Optional.of(categoryCache.values()).orElseThrow(RuTrackerParseException::new);
    }

    public Torrent findTorrentById(long id) throws RuTrackerException {
        try {
            return getTorrent(id, client.fetch(URL.TOPIC.insertId(id)));
        } catch (RuTrackerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTrackerException(e);
        }
    }

    private Attach getAttach(long id, Document document) throws RuTrackerParseException {
        try {
            //noinspection DataFlowIssue
            String[] rawSize = document.select(Query.FILE_SIZE.getValue()).first().text().split(" ");

            return Attach.builder()
                    .id(id)
                    .size(Float.parseFloat(rawSize[0]))
                    .type(SizeType.valueOf(rawSize[1]))
                    .torrent(getTorrent(id, document))
                    .build();
        } catch (Exception e) {
            throw new RuTrackerParseException(e);
        }
    }

    private List<Category> getCategories(Document document) throws RuTrackerParseException {
        try {
            List<Category> categories = document.select(Query.CATEGORY.getValue()).stream()
                    .map(this::mapCategory)
                    .toList();

            if (categories.isEmpty()) {
                throw new RuTrackerParseException();
            }

            return categories;

        } catch (RuTrackerParseException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTrackerParseException(e);
        }
    }

    private Category mapCategory(Element element) {
        return Category.builder()
                .id(Long.parseLong(element.attr("href").replace("viewforum.php?f=", "")))
                .name(element.text())
                .build();
    }

    private Torrent getTorrent(long id, Document document) throws RuTrackerParseException {
        try {
            //noinspection DataFlowIssue
            return Torrent.builder()
                    .id(id)
                    .link(URL.TORRENT_DOWNLOAD_LINK.insertId(id))
                    .magnetLink(document.select(Query.TORRENT_FILE_DOWNLOAD_MAGNET_LINK.getValue()).first().attr("href"))
                    .build();
        } catch (Exception e) {
            throw new RuTrackerParseException(e);
        }
    }
}

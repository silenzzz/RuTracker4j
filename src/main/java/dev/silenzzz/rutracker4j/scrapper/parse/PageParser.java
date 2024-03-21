package dev.silenzzz.rutracker4j.scrapper.parse;

import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.domain.constant.SizeType;
import dev.silenzzz.rutracker4j.scrapper.constant.Query;
import dev.silenzzz.rutracker4j.scrapper.constant.URL;
import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;
import dev.silenzzz.rutracker4j.scrapper.parse.exception.ParseException;
import dev.silenzzz.rutracker4j.scrapper.parse.exception.TopicNotFoundException;
import dev.silenzzz.rutracker4j.scrapper.net.JSoupHttpClient;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchResult;
import dev.silenzzz.rutracker4j.scrapper.search.ref.CategoryReference;
import dev.silenzzz.rutracker4j.scrapper.search.ref.TopicReference;
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
public class PageParser {

    private final JSoupHttpClient client;

    private final Map<Long, Category> categoryCache;

    public PageParser(JSoupHttpClient client) throws RuTracker4jException {
        this.client = client;

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
                throw new ParseException();
            }

            categoryCache = categories;

        } catch (RuTracker4jException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTracker4jException(e);
        }
    }

    public SearchResult parseSearchResultPage(String url) throws RuTracker4jException {
        Document document = client.fetch(url);
        SearchResult result = new SearchResult();

        document.select(Query.TOPIC_ROW.getValue())
                .forEach(e -> result.addTopicReference(TopicReference.builder()
                        .id(Long.parseLong(e.select(Query.TOPIC_TITLE.getValue()).attr("data-topic_id")))
                        .title(e.select(Query.TOPIC_TITLE_ROW.getValue()).text())

                        .categoryReference(CategoryReference.builder()
                                .id(Long.parseLong(e.select(Query.TOPIC_CATEGORY_ROW.getValue())
                                        .attr("href")
                                        .replace("viewtopic.php?t=", "")))

                                .title(e.select(Query.TOPIC_CATEGORY_ROW.getValue()).text())
                                .build())
                        .build())
                );

        return result;
    }

    public static String getErrorMessage(Document document) throws ParseException {
        try {
            //noinspection DataFlowIssue
            return document.select(Query.LOGIN_ERROR.getValue()).first().text();
        } catch (NullPointerException e) {
            throw new ParseException(e);
        }
    }

    public Topic findTopicById(long id) throws RuTracker4jException {
        try {
            Document document = client.fetch(URL.TOPIC.insertId(id));

            if (document.select(Query.TOPIC_NOT_FOUND.getValue()).first() != null) {
                throw new TopicNotFoundException(String.format("Topic with given id=%d not found", id));
            }

            //noinspection DataFlowIssue
            return Topic.builder()
                    .id(id)
                    .title(document.select(Query.TOPIC_TITLE.getValue()).first().text())
                    .categories(getCategories(document))
                    .attach(getAttach(id, document))
                    .build();
        } catch (RuTracker4jException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTracker4jException(e);
        }
    }

    public Attach findAttachById(long id) throws RuTracker4jException {
        try {
            return getAttach(id, client.fetch(URL.TOPIC.insertId(id)));
        } catch (RuTracker4jException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTracker4jException();
        }
    }

    public Category findCategoryById(long id) throws ParseException {
        return Optional.ofNullable(categoryCache.get(id)).orElseThrow(ParseException::new);
    }

    public Collection<Category> getAllCategories() throws ParseException {
        return Optional.of(categoryCache.values()).orElseThrow(ParseException::new);
    }

    public Torrent findTorrentById(long id) throws RuTracker4jException {
        try {
            return getTorrent(id, client.fetch(URL.TOPIC.insertId(id)));
        } catch (RuTracker4jException e) {
            throw e;
        } catch (Exception e) {
            throw new RuTracker4jException(e);
        }
    }

    private Attach getAttach(long id, Document document) throws ParseException {
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
            throw new ParseException(e);
        }
    }

    private List<Category> getCategories(Document document) throws ParseException {
        try {
            List<Category> categories = document.select(Query.CATEGORY.getValue()).stream()
                    .map(this::mapCategory)
                    .toList();

            if (categories.isEmpty()) {
                throw new ParseException();
            }

            return categories;

        } catch (ParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    private Category mapCategory(Element element) {
        return Category.builder()
                .id(Long.parseLong(element.attr("href").replace("viewforum.php?f=", "")))
                .name(element.text())
                .build();
    }

    private Torrent getTorrent(long id, Document document) throws ParseException {
        try {
            //noinspection DataFlowIssue
            return Torrent.builder()
                    .id(id)
                    .link(URL.TORRENT_DOWNLOAD_LINK.insertId(id))
                    .magnetLink(document.select(Query.TORRENT_FILE_DOWNLOAD_MAGNET_LINK.getValue()).first().attr("href"))
                    .build();
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }
}

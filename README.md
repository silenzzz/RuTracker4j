# RuTracker4j

A Java library that provides access to RuTracker.org.

## Features

- Search for torrents by keyword
- Get topic details
- Download torrent file

## Documentation

See wiki on [GitHub](https://github.com/silenzzz/RuTracker4j/wiki).

## Installation

1. Add Jitpack repository in `<repositories>` section in __`pom.xml`__:

    ```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    ```

2. Add RuTracker4j dependency in `<dependencies>` section in __`pom.xml`__:

    ```xml
    <dependency>
        <groupId>dev.silenzzz</groupId>
    <artifactId>RuTracker4j</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```

## Usage

### Create rutracker client

```java
import com.example.RuTracker4j;
import dev.silenzzz.rutracker4j.RuTracker4jDefaultClient;
import dev.silenzzz.rutracker4j.scrapper.net.AccountCredentials;

//...//
RuTracker4j ruTracker = new RuTracker4jDefaultClient(new AccountCredentials("user", "pass"));
///...//
```

### Get topic

```java
//...//
Topic topic = ruTracker.findTopicById(633781);
///...//
```

TODO more examples

## License

Released under the MIT License. See [LICENSE](LICENSE) for more.

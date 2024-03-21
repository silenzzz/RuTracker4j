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

```java
import com.example.RuTracker4j;

public class Main {
    public static void main(String[] args) {
        RuTracker4j ruTracker = new RuTracker4j("your_api_key");
    }
}
```

## License

Released under the MIT License. See [LICENSE](LICENSE) for more.

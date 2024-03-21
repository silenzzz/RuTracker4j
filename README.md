# RuTracker4j

Это Java библиотека для работы с RuTracker.org через их API.

## Установка

Чтобы использовать эту библиотеку, добавьте следующую зависимость в ваш файл `pom.xml`:

```xml

<dependency>
    <groupId>com.example</groupId>
    <artifactId>RuTracker4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Использование

Пример использования библиотеки:

```java
import com.example.RuTracker4j;

public class Main {
    public static void main(String[] args) {
        RuTracker4j ruTracker = new RuTracker4j("your_api_key");
        // Далее используйте методы библиотеки для взаимодействия с RuTracker.org
    }
}
```

## Документация

Для подробной документации по использованию библиотеки, обратитесь к [JavaDocs](link).

## Вклад

Если вы хотите внести свой вклад в развитие проекта, пожалуйста, ознакомьтесь с [CONTRIBUTING.md](link).

## Лицензия

Этот проект лицензирован под [MIT лицензией](link).

# RuTracker4j

A Java library that provides access to RuTracker.org API.

## Features

|   |   |   |
|---|---|---|
|   |   |   |
|   |   |   |
|   |   |   |
|   |   |   |



- Search for torrents by keyword
- Get topic details
- Download torrent file

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
        <groupId>com.github.DeMmAge</groupId>
        <artifactId>Simple-Yaml-Transformer</artifactId>
        <version>${project.version}</version>
    </dependency>
    ```

## Usage

```java
import com.example.RuTracker4j;

public class Main {
    public static void main(String[] args) {
        RuTracker4j ruTracker = new RuTracker4j("your_api_key");
        // Далее используйте методы библиотеки для взаимодействия с RuTracker.org
    }
}
```

## License

Released under the MIT License. See [LICENSE](LICENSE) for more.

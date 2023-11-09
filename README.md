# RUTRACKER-API

## Usage:

### Default client usage:

```java
RutrackerClient client = new DefaultRutrackerClient(new AccountCredentials("username", "password"));
```

### Docs

####
Default implementation: ``DefaultRutrackerClient``


| Class           | Mehod                        | Docs                    |
|-----------------|------------------------------|-------------------------|
| RutrackerClient | Topic findTopicById(long id) | Find a topic by id      |
| RutrackerClient | User findUserById(long id)   | Find user by id         |
| RutrackerClient | findTorrentFileById(long id) | Find torrent file by id |
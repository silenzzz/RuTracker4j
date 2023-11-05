package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {

    private long id;
    private String nickname;
    // TODO: 12.10.2023
    //private String joined;
    private String country;
    private long messagesCount;

}

package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    private String nickname;
    // TODO: 12.10.2023
    //private String joined;
    private Country country;
    private long messagesCount;

    // TODO: 05.11.2023 Separate for year and month
    private String seniority;

    /**
     * Nullable from topic
     */
    private List<Giveaway> giveaways;

}

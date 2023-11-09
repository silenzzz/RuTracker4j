package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    private String nickname;
    private Country country;
    private long messagesCount;

    private Date registered;
    private Date lastVisit;
    // TODO: 05.11.2023 Separate for year and month
    private String seniority;

    private List<Giveaway> giveaways;

}

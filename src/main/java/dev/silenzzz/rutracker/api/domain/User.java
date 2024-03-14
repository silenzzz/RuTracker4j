package dev.silenzzz.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Value
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {

    String nickname;
    Country country;
    long messagesCount;

    Date registered;
    Date lastVisit;
    // TODO: 05.11.2023 Separate for year and month NOSONAR
    String seniority;

    List<Giveaway> giveaways;

}

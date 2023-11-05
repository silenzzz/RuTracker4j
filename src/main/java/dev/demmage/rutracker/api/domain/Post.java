package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@SuperBuilder
public class Post extends BaseEntity {

    private Date date;
    private User user;
    private String bodyText;
    private String bodyHtml;

}

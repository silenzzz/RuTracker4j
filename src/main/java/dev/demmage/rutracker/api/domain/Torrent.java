package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class Torrent extends BaseEntity {

    private String link;
    private String magnetLink;

}

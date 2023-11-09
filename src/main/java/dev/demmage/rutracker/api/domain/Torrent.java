package dev.demmage.rutracker.api.domain;

import dev.demmage.rutracker.api.constant.ValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class Torrent extends BaseEntity {

    private String link;
    private String magnetLink;

    // TODO: 09.11.2023
    // private List<TorrentFile>

    private float sizeValue;
    private ValueType valueType;

}

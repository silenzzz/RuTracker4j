package dev.demmage.rutracker.api.domain;

import dev.demmage.rutracker.api.constant.SizeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class Torrent extends BaseEntity {

    private String link;

    private String magnetLink;

    private float filesSize;
    private SizeType fileSizeType;

}

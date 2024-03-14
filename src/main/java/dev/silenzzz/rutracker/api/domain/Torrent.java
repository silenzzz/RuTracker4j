package dev.silenzzz.rutracker.api.domain;

import dev.silenzzz.rutracker.api.constant.SizeType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Torrent extends BaseEntity {

    String link;
    String magnetLink;
    float filesSize;
    SizeType fileSizeType;

    public Torrent(long id, String link, String magnetLink, float filesSize, SizeType fileSizeType) {
        super(id);
        this.link = link;
        this.magnetLink = magnetLink;
        this.filesSize = filesSize;
        this.fileSizeType = fileSizeType;
    }
}

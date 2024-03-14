package dev.silenzzz.rutracker.api.ref;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthorReference {

    long id;
    String nickname;

}

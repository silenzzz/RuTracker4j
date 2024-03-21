package dev.silenzzz.rutracker4j.scrapper.net;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    INVALID_LOGIN_OR_PASSWORD("Вы ввели неверное/неактивное имя пользователя или неверный пароль", "Invalid login or password"),
    CAPTCHA_TRY_AGAIN("Пожалуйста, введите код подтверждения (символы, изображенные на картинке)", "Entered captcha is incorrect, try again"),
    ;

    private final String ruValue;
    private final String enValue;

}

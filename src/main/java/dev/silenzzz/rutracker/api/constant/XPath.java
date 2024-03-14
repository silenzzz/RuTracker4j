package dev.silenzzz.rutracker.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum XPath {

    USER(""),
    SUPER_CATEGORIES("/html/body/div[4]/div[1]/div[3]/table/tbody/tr/td/div/table[1]/tbody/tr/td[1]/table/tbody/tr/td[2]"),
    USER_SENIORITY("/html/body/div[4]/div[1]/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td/b"),

    MESSAGES_COUNT("/html/body/div[4]/div[1]/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/div/ul/li[1]/a/b"),
    REGISTERED("/html/body/div[4]/div[1]/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td[2]/table/tbody/tr[2]/td/b"),
    LAST_VISIT("/html/body/div[4]/div[1]/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td[1]/table/tbody/tr[1]/td"),

    CATEGORY("/html[1]/body[1]/div[4]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[2]/a[3]"),
    ALL_CATEGORIES("//*[@id=\"fs-main\"]"),

    TORRENT_MAGNET_LINK("/html/body/div[4]/div[1]/div[3]/table/tbody/tr/td/div/table[3]/tbody[2]/tr[1]/td[2]/div[2]/table[1]/tbody/tr[5]/td[2]/ul/li[2]/a"),
    TORRENT_LINK("/html[1]/body[1]/div[4]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/table[3]/tbody[2]/tr[1]/td[2]/div[2]/table[1]/tbody[1]/tr[2]/td[3]/a[1]"),
    TORRENT_FILE_SIZE("/html/body/div[4]/div[1]/div[3]/table/tbody/tr/td/div/table[3]/tbody[2]/tr[1]/td[2]/div[2]/table[1]/tbody/tr[5]/td[2]/ul/li[1]/span"),
    USER_PROFILE_NAME("/html[1]/body[1]/div[4]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]/span[1]/a[1]")
    ;

    private final String value;

}

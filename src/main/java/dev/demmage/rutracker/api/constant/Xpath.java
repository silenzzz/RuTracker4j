package dev.demmage.rutracker.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Xpath {

    CATEGORY("/html[1]/body[1]/div[4]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[2]/a[3]"),
    TORRENT_MAGNET_LINK("/html/body/div[4]/div[1]/div[3]/table/tbody/tr/td/div/table[3]/tbody[2]/tr[1]/td[2]/div[2]/table[1]/tbody/tr[5]/td[2]/ul/li[2]/a"),
    TORRENT_LINK("/html[1]/body[1]/div[4]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/table[3]/tbody[2]/tr[1]/td[2]/div[2]/table[1]/tbody[1]/tr[2]/td[3]/a[1]");

    final String value;

}

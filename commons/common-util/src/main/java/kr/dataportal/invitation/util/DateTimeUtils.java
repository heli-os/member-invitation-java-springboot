package kr.dataportal.invitation.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public class DateTimeUtils {
    private static final ZoneId KST = ZoneOffset.ofHours(9);

    public static long toEpochMillis(final LocalDateTime localDateTime) {
        return localDateTime.atZone(KST).toInstant().toEpochMilli();
    }
}

package corp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

public class ZoneOperations {
    public static void main(String[] args) {
        Set<String> allZoneList = ZoneId.getAvailableZoneIds();
        System.out.println("allZoneList = " + allZoneList);

        ZoneId zid1 = ZoneId.of("Europe/Jersey");
        System.out.println("zid1 = " + zid1);
        LocalDateTime currentTime1 = LocalDateTime.now(zid1);
        System.out.println("currentTime1 = " + currentTime1);

        ZoneId zid2 = ZoneId.of("UTC+4");
        System.out.println("zid2 = " + zid2);
        LocalDateTime currentTime2 = LocalDateTime.now(zid2);
        System.out.println("currentTime1 = " + currentTime2);

        ZoneId zid3 = ZoneId.of("Etc/GMT-9");
        System.out.println("zoneId = " + zid3);
        LocalDateTime currentTime3 = LocalDateTime.now(zid3);
        System.out.println("currentTime3 = " + currentTime3);

        ZoneId zid4 = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(4));
        System.out.println("zoneId = " + zid4);
        LocalDateTime currentTime4 = LocalDateTime.now(zid4);
        System.out.println("currentTime3 = " + currentTime4);

        for (String s: ZoneId.getAvailableZoneIds()) {
            System.out.println(s);
        }
        System.out.println("Valid TimeZone");
        System.out.println(isValidTimezone("UTS-4"));
        System.out.println(isValidTimezone("GMT+4"));
        System.out.println(TimeZone.getTimeZone(""));
    }
    private static boolean isValidTimezone(String timezone) {
        return !Objects.equals(TimeZone.getTimeZone(timezone).getID(), "GMT");
    }
}

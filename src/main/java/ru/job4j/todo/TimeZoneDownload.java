package ru.job4j.todo;

import java.util.ArrayList;
import java.util.TimeZone;

/**
 * @author: Egor Bekhterev
 * @date: 15.03.2023
 * @project: job4j_todo
 */
public class TimeZoneDownload {

    public static void main(String[] args) {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        for (TimeZone zone : zones) {
            System.out.println(zone.getID() + " : " + zone.getDisplayName());
        }

        var defTz = TimeZone.getDefault();
        System.out.println(defTz.getDisplayName());
    }
}

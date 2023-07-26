package com.example.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimestampController {
    public static  String formatTimestamp(long createdAt) {
        long timeDifference = new Date().getTime() - createdAt;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifference);
        long days = TimeUnit.MILLISECONDS.toDays(timeDifference);

        if (minutes == 0) {
            return "Now";
        } else if (minutes < 60) {
            return minutes + "m";
        } else if (hours < 24) {
            return hours + "h";
        } else {
            Date tweetDate = new Date(createdAt);
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM");
            return dateFormat.format(tweetDate);
        }
    }
}

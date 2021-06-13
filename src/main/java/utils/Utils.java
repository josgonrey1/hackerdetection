package utils;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static Date epochToDate(String epoch) throws IOException {
        return new Date(Long.parseLong(epoch) * 1000);
    }

    public static long timeCalculation(String time1, String time2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.getDefault());
        Date firstDate = sdf.parse(time1);
        Date secondDate = sdf.parse(time2);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}

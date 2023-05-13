package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

public class DateUtils
{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", new Locale("tr"));
    private static final SimpleDateFormat cdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));

    public static String GetCurrentData() {
        Date today = Calendar.getInstance().getTime();
        return sdf.format(today);
    }

    public static String GetCustomDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, day);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.YEAR, year);
        Date expire = cal.getTime();
        return sdf.format(expire);
    }

    public static String getDate(int addYear, int addMonth, int addDay, String dateformat) {
        Calendar cal = getYear();
        cal.add(Calendar.DAY_OF_MONTH, addDay);
        cal.add(Calendar.MONTH, addMonth);
        cal.add(Calendar.YEAR, addYear);
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        return "" + sdf.format(cal.getTime());
    }
    public static String changeDateFormat(String date,String actualDateFormat, String expectedDateFormat) throws ParseException {
        //  actualDateFormat = actualDateFormat.replaceAll("\\-","/");
        SimpleDateFormat input = new SimpleDateFormat(actualDateFormat);
        Date dateValue = input.parse(date);
        SimpleDateFormat output = new SimpleDateFormat(expectedDateFormat);
        return "" + output.format(dateValue) ;

    }


    public static String getDate(int addYear, int addMonth, int addDay, int addHour, int addMinute, String dateformat) {
        Calendar cal = getYear();
        cal.add(Calendar.DAY_OF_MONTH, addDay);
        cal.add(Calendar.MONTH, addMonth);
        cal.add(Calendar.YEAR, addYear);
        cal.add(Calendar.HOUR, addHour);
        cal.add(Calendar.MINUTE, addMinute);
        SimpleDateFormat cdf = new SimpleDateFormat(dateformat);
        return "" + cdf.format(cal.getTime());
    }

    public static String getCustomDate(int addYear, int addMonth, int addDay, int addHour, int addMinute, String date) throws ParseException {

        Date date1;
        date1 = cdf.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DAY_OF_MONTH, addDay);
        cal.add(Calendar.MONTH, addMonth);
        cal.add(Calendar.YEAR, addYear);
        cal.add(Calendar.HOUR, addHour);
        cal.add(Calendar.MINUTE, addMinute);
        return "" + cdf.format(cal.getTime());
    }

    public static String getCustomMinute(int addMinute) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, addMinute);
        return "" + cdf.format(cal.getTime());
    }

    public static Calendar getYear() {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }

    public static boolean getCurrentHour(String tradingHourStart) {
        int hourStart = parseInt(tradingHourStart.split(":")[0]);
        int hour = parseInt(getDate(0, 0, 0, 0, 0, "HH"));
        boolean tradingHour = hour > hourStart ? true : false;
        return tradingHour;
    }

    public static String changeDateStep(int param) {
        String expectedParam = "" + param;
        expectedParam = expectedParam.length() == 1 ? "0" + expectedParam : expectedParam;
        return expectedParam;
    }

    public static List<String> splitTimeList(List<String> timeList) {
        List<String> list = new ArrayList<>();

        timeList.forEach((element) -> {
            String[] str = element.split(" ");
            list.add(str[0]);
        });

        return list;
    }

    public static boolean compareToDate(List<String> timeList, String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(date1);
        Date endDate = sdf.parse(date2);
        for (int i = 0; i < timeList.size(); i++) {
            if (sdf.parse(timeList.get(i)).compareTo(startDate) < 0 || sdf.parse(timeList.get(i)).compareTo(endDate) > 0) {
                return false;
            }
        }
        return true;
    }

}

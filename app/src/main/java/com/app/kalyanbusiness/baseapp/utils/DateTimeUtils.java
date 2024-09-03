package com.app.kalyanbusiness.baseapp.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {

    // DateTime formats

    //public static final String DATETIME_12_MDY_FORMAT = "MM-dd-yyyy hh:mm:ss a";
    public static final String DATETIME_12_WITH_MONTH_NAME_FORMAT = "dd/MMMM/yyyy hh:mm:ss a";
    public static final String DATE_DMY_HYPHEN_FORMAT = "dd-MM-yyyy";
    public static final String DATE_YMD_HYPHEN_FORMAT = "yyyy-MM-dd";
    public static final String DATE_WITH_DAY_FORMAT = "EEE, MM/dd/yyyy";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String DATE_WITH_MONTH_NAME_FORMAT = "dd/MMMM/yyyy";
    public static final String DATETIME_24_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_DMMMY_TIME_12H_FORMAT = "dd MMM yyyy | hh:mm a";
    public static final String TIME_24H_FORMAT = "HH:mm:ss";
    public static final String TIME_12H_FORMAT = "hh:mm:ss a";
    public static final String TIME_12H_FORMAT_WITHOUT_SECONDS = "hh:mm a";
    public static final String TIME_24H_FORMAT_WITHOUT_SECONDS = "HH:mm";

    public static Float getTimeDiffInHours(String startTime, String endTime) {
        //millisecond
        String[] st = startTime.split(":");
        String[] et = endTime.split(":");

        int h1 = Integer.valueOf(st[0]);
        int m1 = Integer.valueOf(st[1]);

        int h2 = Integer.valueOf(et[0]);
        int m2 = Integer.valueOf(et[1]);

        Float stHours = (float) ((m1 / 60) + h1);
        Float etHours = (float) ((m2 / 60) + h2);

        return etHours - stHours;
    }

    public static Date getDate(int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTime();
    }

    public static String getAge(Calendar dob) {
        if (dob == null)
            return "not added yet";

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
            age--;

        Integer ageInt = age;
        return ageInt + " year(s)";
    }

    public static String customFormatting(String pattern, String dateString) {
        if (dateString == null)
            return "";

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsed = null;
        try {
            parsed = parser.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        assert parsed != null;
        calendar.setTime(parsed);

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(calendar.getTime());
    }

    public static boolean isValidDateTimeForCurrentAppointment(String aptDate, String aptStartTime, String aptEndTime) {
        if (DateTimeUtils.isSameDay(DateTimeUtils.toCalendar(DateTimeUtils.getDateFromString(aptDate)), Calendar.getInstance()))
            return DateTimeUtils.isCurrentTimeInBetween(aptStartTime, aptEndTime);

        return false;
    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static boolean isSameDay(Calendar c1, Calendar c2) {
        boolean same = c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);

        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
            same = false;

        if (c1.get(Calendar.DATE) != c2.get(Calendar.DATE))
            same = false;

        return same;
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isTimePassed(@NotNull String dateTime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            Date currentDateTimeRef = sdf.parse(sdf.format(Calendar.getInstance().getTime()));
            Date dateTimeRef = convertToDateRefOfFormat(dateTime, format);

            assert dateTimeRef != null;
            assert currentDateTimeRef != null;
            return currentDateTimeRef.compareTo(dateTimeRef) >= 0;
        } catch (AssertionError | Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void pickDate(Context context, OnDateSelect onDateSelect) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener fromListener = (view1, year, monthOfYear, dayOfMonth) -> {

            Calendar calFrom = Calendar.getInstance();
            calFrom.set(year, monthOfYear, dayOfMonth);
            String dateString = new SimpleDateFormat("dd MMM yyyy").format(calFrom.getTime());

            onDateSelect.onDateSelected(dateString, calFrom);
        };
        new DatePickerDialog(context, fromListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    public interface OnDateSelect {
        void onDateSelected(String formattedDate, Calendar calendar);
    }

    public static Date getDateFromString(String date) {
        if (date == null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Calendar getCalendarFromDateString(String date) {
        Date date1 = getDateFromString(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        return calendar;
    }

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static long getDiffInDays(Date startDate, Date endDate) {
        try {
            // Difference in Milliseconds
            long different = endDate.getTime() - startDate.getTime();
            return TimeUnit.MILLISECONDS.toDays(different);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getFormattedDateToShow(String date) {
        if (date == null)
            return "";
        String[] dateParts = date.split("T");
        String onlyDatee = dateParts.length > 0 ? dateParts[0] : "";
        String[] onlyDateParts = onlyDatee.split("-");
        if (onlyDateParts.length >= 3) {
            try {
                return getFormattedDateToShow(Integer.parseInt(onlyDateParts[0]), Integer.parseInt(onlyDateParts[1])
                        , Integer.parseInt(onlyDateParts[2]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getFormattedDateToShow(int year, int month, int day) {
        return day + "/" + month + "/" + year;
    }

    // Converts given dateTime Reference to DateTime String
    public static String toDateStr(Date dateTime, String format) {
        return new SimpleDateFormat(format, Locale.ROOT).format(dateTime);
    }

    /**
     * @return DateTime with newly specified format
     */
    public static String changeFormat(String dateTime, String fromFormat, String toFormat) {
        if (dateTime == null || dateTime.isEmpty())
            return "";
        SimpleDateFormat sfdInput = new SimpleDateFormat(fromFormat, Locale.ROOT);
        //SimpleDateFormat sdfOutput = new SimpleDateFormat(toFormat, Locale.ROOT);

        try {
            if (dateTime.contains("+"))
                dateTime = dateTime.substring(0, dateTime.lastIndexOf("+"));

            Date date = sfdInput.parse(dateTime);
            assert date != null;
            return android.text.format.DateFormat.format(toFormat, date).toString();
            //return sdfOutput.format(date);
        } catch (AssertionError | Exception e) {
            e.printStackTrace();
        }

        return dateTime; // Return Same DateTime - only iff unable to change format
    }

    /**
     * @return DateTime in newly specified Format
     */
    public static String changeFormat(String dateTime, String fromFormat, int toFormat) {
        SimpleDateFormat sfdInput = new SimpleDateFormat(fromFormat, Locale.ROOT);

        try {
            if (dateTime.contains("+"))
                dateTime = dateTime.substring(0, dateTime.lastIndexOf("+"));

            Date date = sfdInput.parse(dateTime);
            assert date != null;
            return DateFormat.getDateInstance(toFormat).format(date);
        } catch (AssertionError | Exception e) {
            e.printStackTrace();
        }

        return dateTime;
    }

    /**
     * @return Current Calendar date in specified format
     */
    public static String getCurrentDateStr(String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            return new SimpleDateFormat(format, Locale.ROOT).format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * @return Time Difference in Milli Seconds
     */
    public static long findTimeDifferenceInMilliSeconds(String startTime, String endTime, boolean in24HourPattern) {
        Date startTimeRef = convertToDateRefOfFormat(startTime, in24HourPattern ? TIME_24H_FORMAT : TIME_12H_FORMAT);
        Date endTimeRef = convertToDateRefOfFormat(endTime, in24HourPattern ? TIME_24H_FORMAT : TIME_12H_FORMAT);

        if (startTimeRef == null || endTimeRef == null)
            return 0;

        return endTimeRef.getTime() - startTimeRef.getTime();
    }

    /**
     * @return TRUE if currentTime >= startTime && currentTime <= endTime
     */
    public static boolean isCurrentTimeInBetween(String startTime, String endTime) {
        Date currentTimeRef = getSystemCurrentTimeRefOnly(true);
        Date startTimeRef = convertToDateRefOfFormat(startTime, TIME_24H_FORMAT);
        Date endTimeRef = convertToDateRefOfFormat(endTime, TIME_24H_FORMAT);

        if (currentTimeRef == null || startTimeRef == null || endTimeRef == null)
            return true;

        return currentTimeRef.compareTo(startTimeRef) >= 1 && currentTimeRef.compareTo(endTimeRef) <= 0;
    }

    /**
     * @return Reference of Converted given String date/time/datetime to specified format
     */
    public static Date convertToDateRefOfFormat(String dateTime, String toFormat) {
        try {
            return new SimpleDateFormat(toFormat, Locale.ROOT).parse(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return Date Reference for a specified Time format
     */
    public static Date getSystemCurrentTimeRefOnly(boolean is24HourFormat) {
        try {
            String format = TIME_12H_FORMAT;
            if (is24HourFormat)
                format = TIME_24H_FORMAT;

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(sdf.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return Date String for a specified Time format
     */
    public static String getSystemCurrentTimeTextOnly(boolean is24HourFormat) {
        try {
            String format = TIME_12H_FORMAT;
            if (is24HourFormat)
                format = TIME_24H_FORMAT;

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return Convert given milli seconds to specified Time format
     */
    public static String convertMilliSecondsToTimeFormat(long millis) {
        return String.format(Locale.ROOT, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public static String trimDate(String date) {
        if (!date.contains("+"))
            return date;
        return date.substring(0, date.indexOf("+"));
    }

    public static void pickDateAndTime(Context context, Date minDate, OnDateSelect onDateSelect) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener fromListener = (view1, year, monthOfYear, dayOfMonth) -> {

            Calendar calFrom = Calendar.getInstance();
            calFrom.set(year, monthOfYear, dayOfMonth);
//            String dateString = new SimpleDateFormat("dd MMM yyyy").format(calFrom.getTime());
            _pickTime(context, calFrom, onDateSelect);
//            onDateSelect.onDateSelected(dateString,calFrom);
        };

        DatePickerDialog datePicker = new DatePickerDialog(context, fromListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        if (minDate != null)
            datePicker.getDatePicker().setMinDate(minDate.getTime());
        datePicker.show();
    }

    private static void _pickTime(Context context, Calendar cal, OnDateSelect onDateSelect) {
        if (cal == null)
            cal = Calendar.getInstance();

        Calendar finalCal = cal;

        TimePickerDialog.OnTimeSetListener listener = (timePicker, hourOfDay, minute) -> {
            finalCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            finalCal.set(Calendar.MINUTE, minute);

            String dateString = new SimpleDateFormat(DATETIME_24_FORMAT).format(finalCal.getTime());
            onDateSelect.onDateSelected(dateString.trim(), finalCal);
        };

        new TimePickerDialog(context, listener, finalCal.get(Calendar.HOUR_OF_DAY),
                finalCal.get(Calendar.MINUTE), false).show();
    }

    public static void pickTime(Context context, Calendar cal, OnDateSelect onDateSelect) {
        if (cal == null)
            cal = Calendar.getInstance();

        Calendar finalCal = cal;
        TimePickerDialog.OnTimeSetListener listener = (timePicker, hourOfDay, minute) -> {
            finalCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            finalCal.set(Calendar.MINUTE, minute);


            String dateString = new SimpleDateFormat("hh:mm a").format(finalCal.getTime());
//            String dateString = new SimpleDateFormat("hh:mm a").format(finalCal.getTime());
            onDateSelect.onDateSelected(dateString, finalCal);

        };

        new TimePickerDialog(context, listener, finalCal.get(Calendar.HOUR_OF_DAY),
                finalCal.get(Calendar.MINUTE), false).show();
    }

    public static void pickServerTime(Context context, Calendar cal, OnDateSelect onDateSelect) {
        if (cal == null)
            cal = Calendar.getInstance();

        Calendar finalCal = cal;
        TimePickerDialog.OnTimeSetListener listener = (timePicker, hourOfDay, minute) -> {
            finalCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            finalCal.set(Calendar.MINUTE, minute);


            String dateString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(finalCal.getTime());
//            String dateString = new SimpleDateFormat("hh:mm a").format(finalCal.getTime());
            onDateSelect.onDateSelected(dateString, finalCal);

        };

        new TimePickerDialog(context, listener, finalCal.get(Calendar.HOUR_OF_DAY),
                finalCal.get(Calendar.MINUTE), false).show();

    }
}

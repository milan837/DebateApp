package com.group.milan.debate.debate.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by nakul on 19/01/17.
 */

public class UtilsClass {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }

    /**Use this factory method to check date is  valid or not.*/
    public static boolean isValidDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);

        try {
            format.parse(input.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**Use this factory method to Convert image inputStream in Bytes[].*/
    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    /**Use this factory method to convert paise to rupees.*/
    public static String getConvertPaiseTORupees (int paise) {
        String price = null;
        if ( paise > 0) {
            price = String.valueOf(paise / 100);
        }
        return price;
    }

    /**Use this factory method to convert time milliseconds to SimpleTimeFormat.*/
    public static String getConvertTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        TimeZone utc = TimeZone.getTimeZone("Asia/Kolkata");
        formatter.setTimeZone(utc);
        return formatter.format(calendar.getTime());
    }

    /**Use this factory method to convert time milliseconds to SimpleTimeFormat.*/
    public static String getConvertDate(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone utc = TimeZone.getTimeZone("Asia/Kolkata");
        formatter.setTimeZone(utc);
        return formatter.format(calendar.getTime());
    }

    public static String getDayFromTimestamp(long timestamp){

        String getdate=getConvertDate(timestamp);
        String date[]=getdate.split(" ");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        TimeZone utc = TimeZone.getTimeZone("UTC");
        formatter.setTimeZone(utc);
        String newDate =formatter.format(calendar.getTime());

        if(date[0] == date[0]+1 && date[1].equals(newDate)){
            return "yestarday";
        }else{
            return getConvertDate(timestamp);
        }
    }

    private String toDate(long timestamp) {
        Date date = new Date(timestamp * 1000 -  24 * 60 * 60 * 1000);
        return new SimpleDateFormat("yyyy-MM-dd").format(date).toString();
    }

}

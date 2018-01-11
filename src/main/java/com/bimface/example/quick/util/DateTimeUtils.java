package com.bimface.example.quick.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

    public static Date parseBimfaceDateStr(String bimfaceDateStr) throws ParseException {
        return format.parse(bimfaceDateStr);
    }
}

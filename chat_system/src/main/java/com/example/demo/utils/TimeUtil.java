package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

  private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static Date formatDate(Date date) {
    try {
      return dateFormat.parse(dateFormat.format(date));

    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}

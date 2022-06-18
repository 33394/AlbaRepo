package com.example.demo.utils;

import java.util.UUID;

public class QQNumberUtil {

  public static Integer getQQNumber() {

    String str = UUID.randomUUID().toString();

    str = str.substring(0, str.indexOf("-"));
    StringBuilder l = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) > '0' && str.charAt(i) < '9')
        l.append(str.charAt(i));
      else
        l.append((int) str.charAt(i));
    }

    return Integer.parseInt(replaceStartWithZero(l.substring(0, chooseLength())));

  }

  private static String replaceStartWithZero(String str) {
    if (str.startsWith("0"))
      return replaceStartWithZero(str.substring(1, str.length() - 2));
    else return str;
  }

  private static int chooseLength() {
    int[] length = {6, 7, 8, 9};
    return length[(int) (Math.random() * 10) % 4];
  }
}

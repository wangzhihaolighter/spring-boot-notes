package com.example.i18n.core.util;

import java.util.UUID;

public class IdUtils {

  public static String uuid() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}

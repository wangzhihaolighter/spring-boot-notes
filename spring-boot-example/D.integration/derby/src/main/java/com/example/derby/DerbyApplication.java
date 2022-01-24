package com.example.derby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DerbyApplication {

  public static void main(String[] args) {
    initDerby();
    SpringApplication.run(DerbyApplication.class, args);
  }

  public static void initDerby() {
    System.setProperty("derby.stream.error.file", "./temp/derby_data/derby.log");
  }
}

package com.example.springframework.boot.aop.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SimpleService {
    public void doServiceOne() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doServiceTwo() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doServiceThree() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

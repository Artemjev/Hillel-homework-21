package com.hillel.artemjev.homework21;

import com.hillel.artemjev.homework21.util.FibonacciCalc;
import com.hillel.artemjev.homework21.util.NioFileUtil;
import com.hillel.artemjev.homework21.util.PhonesSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Object monitor = new Object();
        NioFileUtil fileUtil = new NioFileUtil("out.txt");
        fileUtil.createFileIfNotExist();
        fileUtil.cleanFile();

        //-------------------------------------------------------------------
        Thread th1 = new Thread(() -> {
            long resFibCalc = new FibonacciCalc().calc(15);
            synchronized (monitor) {
                fileUtil.writeString("фибоначи: " + resFibCalc + "\n");
            }
        });

        //-------------------------------------------------------------------
        Thread th2 = new Thread(() -> {
            double sum = 0;
            try (Scanner scanner = new Scanner(new File("numbers.txt"))) {
                int i = 0;
                while (scanner.hasNextDouble() && i < 10) {
                    sum += scanner.nextDouble();
                    ++i;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            synchronized (monitor) {
                fileUtil.writeString("сумма: " + sum + "\n");
            }
        });

        //-------------------------------------------------------------------
        Thread th3 = new Thread(() -> {
            NioFileUtil fileUtilRead = new NioFileUtil("test.txt");
            PhonesSelector phonesSelector = new PhonesSelector();

            Set<String> phoneSet = new HashSet<>();
            fileUtilRead.readByLine(str -> {
                phoneSet.addAll(phonesSelector.getPhoneNumbers(str)); //выбранные номера не повторяются;
            });
            synchronized (monitor) {
                fileUtil.writeString("номера: ");
                phoneSet.forEach(phoneStr -> fileUtil.writeString(phoneStr + ", "));
                fileUtil.writeString("\n");
            }
        });

        th1.start();
        th2.start();
        th3.start();

        try {
            th1.join();
            th2.join();
            th3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Все операции завершены");
    }
}

package com.yue;

import org.openjdk.jol.info.ClassLayout;

public class T {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());

        int[] a= new int[10];
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}

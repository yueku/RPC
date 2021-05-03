package com.yue.common;

import java.util.Arrays;

public class CollectionUtils {
    public static void print(Object[] objects) {
        Arrays.stream(objects).forEach(obj -> {
            System.out.println();
        });
    }
}

package com.debasish.todolist.utils;

import java.util.UUID;

public class UUIDGenerator {


    /**
     * Function responsible for generating randomUUID
     * @param num
     * @return
     */
    public static String randomUUID(int num) {
        UUID uid = null;
        for (int i = 1; i < num; i++) {

            /***** Generating Random UUID's *****/
            uid = UUID.randomUUID();
        }
        return uid.toString();
    }
}

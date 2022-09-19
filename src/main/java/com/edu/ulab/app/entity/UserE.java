package com.edu.ulab.app.entity;


import lombok.Data;
import lombok.NonNull;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class UserE implements HavingId {
    private static AtomicLong idCounter = new AtomicLong(0);

    private Long id;
    @NonNull
    private String fullName;
    @NonNull
    private String title;
    @NonNull
    private Integer age;

    public UserE(String fullName, String title, int age) {
        this.id = idCounter.getAndAdd(1);
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }
}

package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class BookE implements HavingId {
    private static AtomicLong idCounter = new AtomicLong(0);

    private Long id;
    @NonNull
    private Long userId;

    @NonNull
    private String title;
    @NonNull
    private String author;
    private long pageCount;

    public BookE(Long userId, String title, String author) {
        this.id = idCounter.getAndAdd(1);
        this.userId = userId;
        this.title = title;
        this.author = author;
    }
}

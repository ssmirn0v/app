package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserBookE implements HavingId {
    @NonNull
    private Long userId;
    private Set<Long> booksIds = new HashSet<>();

    @Override
    public Long getId() {
        return userId;
    }
}

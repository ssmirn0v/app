package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class UserBookE implements HavingId {
    @NonNull
    private Long userId;
    private Set<Long> booksIds;

    @Override
    public Long getId() {
        return userId;
    }
}

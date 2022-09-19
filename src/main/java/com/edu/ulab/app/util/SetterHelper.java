package com.edu.ulab.app.util;

import java.util.function.Consumer;

public class SetterHelper {
    public static <V> void setIfNotNull(V value, Consumer<V> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}

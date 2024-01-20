package com.craftinginterpreters.util;

public class Objects {
    private Objects() {
        super();
    }

    public static void checkNotNull(Object obj) {
        if (obj == null) throw new NullPointerException();
    }
}

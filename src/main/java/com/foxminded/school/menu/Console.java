package com.foxminded.school.menu;

public interface Console {
    void print(CharSequence text);

    String readString();

    default void println(CharSequence text) {
        print(text);
        print("\n");
    }
}


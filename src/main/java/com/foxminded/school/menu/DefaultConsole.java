package com.foxminded.school.menu;

import java.util.Scanner;

class DefaultConsole implements Console {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(CharSequence text) {
        System.out.print(text);
    }

    @Override
    public String readString() {
        return scanner.next();
    }
}

package com.foxminded.school.menu;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
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

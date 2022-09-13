package ru.mmtr;

import ru.mmtr.view.Console;

public class Main {
    public static void main(String[] args) {
        Console test = new Console();
        test.choice(args[0]);
        test.actions();

    }
}

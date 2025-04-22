package org.example;

import org.example.datalib.console.Console;
import org.example.datalib.console.StandardConsole;
import org.example.utils.Runner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        //        export lab5="/home/studs/s471800/organizations.json"
        Console console = new StandardConsole(new BufferedReader(new InputStreamReader(System.in)));
        Runner runner = new Runner(console);

        console.printGritting("Приветствуем вас в приложении для модерации списка Организаций!");
        console.printGritting("Введите 'help' для получения справки по командам");

        runner.interactiveMode();
    }
}

package org.example.datalib.console;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;

@Getter
@Setter
public class StandardConsole implements Console {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    private BufferedReader reader;

    public StandardConsole(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void printerr(Object obj) {
        System.out.println(RED + obj + RESET);
    }

    @Override
    public void printGritting(Object obj) {
        System.out.println(GREEN + obj + RESET + "\n");
    }

    @Override
    public String readln() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            printerr("Ошибка чтения ввода: " + e.getMessage());
            return "";
        }
    }

    @Override
    public void setReader(BufferedReader reader) {

    }

    @Override
    public BufferedReader getReader() {
        return null;
    }
}

package org.example;

import org.example.datalib.console.Console;
import org.example.datalib.console.StandardConsole;
import org.example.datalib.models.ExecStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.example.datalib.SocketEnvironment.ENTER_PRESSED_ON_KEYBOARD;

public class Main {

    public static void main(String[] args) {
        Console console = new StandardConsole(new BufferedReader(new InputStreamReader(System.in)));

        console.printGritting("Приветствуем вас в приложении для модерации списка Организаций!");
        console.printGritting("Введите 'help' для получения справки по командам");

        try {
            UPDClient client = new UPDClient();
            String line;
            while ((line = console.readln()) != null) {
                String[] tokens;
                if(line.isEmpty()){
                    tokens = new String[]{ENTER_PRESSED_ON_KEYBOARD};
                }else {
                    tokens = line.trim().split(" ");
                }
                if (!client.send(tokens)) continue;

                ExecStatus execStatus;
                do{
                    execStatus = client.receive();
                    if (execStatus.isOk())
                        if (execStatus.isGritting()) {
                            console.printGritting(execStatus);
                        } else {
                            console.println(execStatus);
                        }
                    else {
                        console.printerr(execStatus);
                    }
                }while (execStatus.isNext());
                if (execStatus.isStopServer()) break;
            }
        }catch (IOException e) {
            System.err.println("Ошибка работы с сервером: " + e.getMessage());
        }
    }
}

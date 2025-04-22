package org.example.utils;

import org.example.commands.Command;
import org.example.datalib.console.Console;
import org.example.datalib.exceptions.ExitException;
import org.example.datalib.models.ExecStatus;
import org.example.managers.CommandManager;
import org.example.module.Organization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Запускает программу. Запрашивает у пользователя ввод с консоли, парсит его в команды и запускает
 */
public class Runner {
    private final CommandManager commandManager = CommandManager.getInstance();
    private final Console console;
    private final Asker asker;
    private final Map<String, Integer> scriptStack = new HashMap<>();

    public Runner(Console console) {
        this.console = console;
        asker = new Asker(console);
    }

    /**
     * Интерактивный режим. Считывает команды, вводимые пользователем в стандартный поток
     */
    public void interactiveMode() {
        try {
            String line;
            while ((line = console.readln()) != null) {
                String[] tokens = line.trim().split(" ");

                if (!tokens[0].isEmpty()) {
                    ExecStatus execStatus = runCommand(tokens);
                    if (execStatus.isOk())
                        console.println(execStatus);
                    else
                        console.printerr(execStatus);
                }
            }
        } catch (ExitException e) {
            console.println("Закрываем программу...");
        }
    }

    /**
     * Запускает команду
     * @param tokens Токены команды
     * @return Статус запуска команды
     * @throws ExitException если пользователь ввёл 'exit'
     */
    private ExecStatus runCommand(String[] tokens) throws ExitException {
        if (tokens[0].equalsIgnoreCase("exit"))
            throw new ExitException();

        Command command = commandManager.getCommand(tokens[0]);

        if (command == null)
            return new ExecStatus(false, String.format("Команды %s не существует", tokens[0]));

        if (tokens[0].equals("execute_script")) {
            if (tokens.length != 2)
                return new ExecStatus(false, "Команде 'execute_script' были переданы невалидные аргументы Введите 'help' для справки.");
            console.printGritting(String.format("Запускаем скрипт '%s'", tokens[1]));
            return scriptMode(tokens[1]);
        }

        Organization organization = null;
        if (command.needOrganization()) {
            try {

                organization = asker.askOrganization();

            } catch (ExitException e) {
                return new ExecStatus(false, "Отменяем операцию...");
            } catch (IllegalArgumentException e) { // проверка на валидность аргументов
                return new ExecStatus(false, "Вы передали невалидные аргументы в программу! Введите 'help' для справки");
            }
        }

        ExecStatus exec = command.execute(organization, tokens);
        commandManager.updateHistory(command);

        return exec;
    }


    /**
     * Режим работы с файлами (чтение команд из скрипта)
     */
    private ExecStatus scriptMode(String filename) {
        scriptStack.merge(filename, 1, Integer::sum); // считаем количество запусков всех файлов
        BufferedReader defaultReader = console.getReader();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            console.setReader(reader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split(" ");

                if (tokens.length == 0 || tokens[0].isEmpty())
                    continue; // Пропускаем пустые строки

                console.println("> " + line); // Показываем команду перед выполнением

                if (tokens[0].equals("execute_script")) {
                    if (scriptStack.get(filename) == 50)
                        return new ExecStatus(false, "Достигнут предел рекурсии!");
                }

                ExecStatus execStatus = runCommand(tokens);
                if (!execStatus.isOk())
                    console.printerr(execStatus.getMessage());
                else
                    console.println(execStatus.getMessage());
            }
            return new ExecStatus(true, "Скрипт выполнен успешно.");
        } catch (IOException e) {
            return new ExecStatus(false, "Ошибка чтения файла: " + e.getMessage());
        } catch (ExitException e) {
            return new ExecStatus(false, "Скрипт принудительно завершен.");
        } catch (NullPointerException e) {
            return new ExecStatus(false, "Скрипт содержит невалидную команду! Аварийный выход...");
        }
        finally {
            console.setReader(defaultReader);
            scriptStack.merge(filename, 0, (oldValue, newValue) -> oldValue - 1);
        }
    }
}

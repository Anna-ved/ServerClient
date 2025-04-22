package org.example.managers;

import org.example.UPDServer;
import org.example.commands.Command;
import org.example.datalib.console.Console;
import org.example.datalib.console.StandardConsole;
import org.example.datalib.exceptions.ExitException;
import org.example.datalib.models.ExecStatus;
import org.example.module.Organization;
import org.example.utils.Asker2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.example.datalib.SocketEnvironment.ENTER_PRESSED_ON_KEYBOARD;
import static org.example.datalib.SocketEnvironment.SERVER_STOP_COMMAND;

public class CommandExecManager {
    private final CommandManager commandManager = CommandManager.getInstance();
    private final UPDServer server;
    private final Asker2 asker;
    private final Map<String, Integer> scriptStack = new HashMap<>();

    public CommandExecManager(UPDServer server) {
        this.asker = new Asker2(server);
        this.server = server;
    }

    public ExecStatus runCommand(String[] tokens) throws ExitException, IOException {
        if (server.isCommandReceived(SERVER_STOP_COMMAND)) {
            ExecStatus execStatus = new ExecStatus(false, """
                                    \s
                             ／＞　 フ\s
                    　　　　| 　u　 u|\s
                     　　　／`ミ _x 彡    | Пока - пока :3 |
                    　 　 /　　　 　 |\s
                    　　 /　 ヽ　　 ﾉ\s
                    ／￣|　　 |　|　|\s
                    | (￣ヽ＿_ヽ_)_)\s
                    ＼二つ""".indent(1));
            execStatus.setStopServer(true);
            return execStatus;
        }
        Command command = commandManager.getCommand(tokens[0]);

        if (command == null)
            return new ExecStatus(
                    false,
                    String.format(
                            "Команды %s не существует",
                            tokens[0].equals(ENTER_PRESSED_ON_KEYBOARD)?"Enter":tokens[0]
                    )
            );


        if (tokens[0].equals("execute_script")) {
            if (tokens.length != 2)
                return new ExecStatus(false, "Команде 'execute_script' были переданы невалидные аргументы Введите 'help' для справки.");
            server.sendGritting(String.format("Запускаем скрипт '%s'", tokens[1]));
            return scriptMode(tokens[1]);
        }

        Organization organization = null;
        if (command.needOrganization()) {
            try {
                organization = asker.askOrganization();
            } catch (ExitException e) {
                ExecStatus execStatus = new ExecStatus(false, "Отменяем операцию...");
                return execStatus;
            } catch (IllegalArgumentException e) { // проверка на валидность аргументов
                ExecStatus execStatus = new ExecStatus(false, "Вы передали невалидные аргументы в программу! Введите 'help' для справки");
                return execStatus;
            }
        }

        ExecStatus exec = command.execute(organization, tokens);
        commandManager.updateHistory(command);
        return exec;
    }

    private ExecStatus scriptMode(String filename) {
        Console console = new StandardConsole(new BufferedReader(new InputStreamReader(System.in)));
        scriptStack.merge(filename, 1, Integer::sum); // считаем количество запусков всех файлов
        BufferedReader defaultReader = console.getReader();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            console.setReader(reader);
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split(" ");

                if (tokens.length == 0 || tokens[0].isEmpty())
                    continue; // Пропускаем пустые строки


                ExecStatus execStatus1 = new ExecStatus(true, "> " + line);
                execStatus1.setNext(true);
                server.send(execStatus1); // Показываем команду перед выполнением

                if (tokens[0].equals("execute_script")) {
                    if (scriptStack.get(filename) == 50) {
                        ExecStatus execStatus = new ExecStatus(false, "Достигнут предел рекурсии!");
//                        execStatus.setNext(true);
                        return execStatus;
                    }
                }

                ExecStatus execStatus = runCommand(tokens);
                execStatus.setNext(true);
                if (!execStatus.isOk())
                    server.sendErr(execStatus.getMessage());
                else {
                    server.send(execStatus);
                }
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

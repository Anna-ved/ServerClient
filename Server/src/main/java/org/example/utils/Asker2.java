package org.example.utils;

import org.example.UPDServer;
import org.example.datalib.exceptions.ExitException;
import org.example.managers.CollectionManager;
import org.example.module.*;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.example.datalib.SocketEnvironment.ENTER_PRESSED_ON_KEYBOARD;
import static org.example.datalib.SocketEnvironment.STOP_MULTIPLE_COMMAND;

/**
 * Этот класс создаёт экземпляр организации из данных, введённых пользователем
 */
public class Asker2 {

    private final static String VALID_ERROR_MESSAGE = "Данное значение не валидно! Попробуйте ещё раз";

    private final CollectionManager collectionManager = CollectionManager.getInstance();
    private final UPDServer server;

    public Asker2(UPDServer server) {
        this.server = server;
    }

    //    private long id;                                //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
//    private String name;                            //Поле не может быть null, Строка не может быть пустой
//    private Coordinates coordinates;                //Поле не может быть null
//    private LocalDateTime creationDate;             //Поле не может быть null, Значение этого поля должно генерироваться автоматически
//    private int annualTurnover;                     //Значение поля должно быть больше 0
//    private String fullName;                        //Поле может быть null
//    private Integer employeesCount;                 //Поле может быть null, Значение поля должно быть больше 0
//    private OrganizationType type;                  //Поле может быть null
//    private Address postalAddress;                  //Поле не может быть null
    public Organization askOrganization() throws ExitException, IOException {
        String message = "Для выполнения команды требуется ввести информацию об Организации\n" +
                "* введите '%s' для отмены операции *\n".formatted(STOP_MULTIPLE_COMMAND);
        long id = collectionManager.getFreeId();                                //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        String name = askOrgName(message);                            //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates = askCoordinates(name);                //Поле не может быть null
        LocalDateTime creationDate = LocalDateTime.now();             //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        int annualTurnover = askAnnualTurnover(name);                     //Значение поля должно быть больше 0
        String fullName = askFullName(name);                        //Поле может быть null
        Integer employeesCount = askEmployeesCount(name);                 //Поле может быть null, Значение поля должно быть больше 0
        OrganizationType type = askOrganizationType(name);                  //Поле может быть null
        Address postalAddress = askPostalAddress(name);                  //Поле не может быть null

        return new Organization(id, name, coordinates, creationDate, annualTurnover, fullName, employeesCount, type, postalAddress);
    }

    private String askOrgName(String greetings) throws ExitException, IOException {
        String name;
        do {
            server.send(greetings + "Введите название Организации: ");
            name = server.receive().getFirstToken().trim();
            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();
        } while (name.isEmpty());
        return name;
    }

    private Coordinates askCoordinates(String name) throws ExitException, IOException {
//        Integer x; //Максимальное значение поля: 59, Поле не может быть null
//        Double y; //Максимальное значение поля: 115, Поле не может быть null
        Integer x = null;
        do {
            String message = String.format("Введите координаты \"x\" Организации '%s' (максимальное значение: 59): ", name);
            server.send(message);
            String line = server.receive().getFirstToken().trim();

            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    x = Integer.parseInt(line);
                    if (x > 59) {
                        x = null;
                        server.sendErr(VALID_ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException e) {
                    server.sendErr(VALID_ERROR_MESSAGE);
                }
            }
        } while (x==null);
        Double y = null;
        do {
            String message = String.format("Введите координаты \"y\" Организации '%s' (максимальное значение: 115): ", name);
            server.send(message);
            String line = server.receive().getFirstToken().trim();

            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    y = Double.parseDouble(line);
                    if (y > 115) {
                        y = null;
                        server.sendErr(VALID_ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException e) {
                    server.sendErr(VALID_ERROR_MESSAGE);
                }
            }
        } while (y==null);
        return new Coordinates(x, y);
    }

    private int askAnnualTurnover(String name) throws ExitException, IOException {
        int value = 0;
        do {
            String message = String.format("Введите значение годового оборота Организации '%s': ", name);
            server.send(message);
            String line = server.receive().getFirstToken().trim();

            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    value = Integer.parseInt(line);
                    if (value <= 0) {
                        server.sendErr(VALID_ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException e) {
                    server.sendErr(VALID_ERROR_MESSAGE);
                }
            }
        } while (value <= 0);
        return value;
    }
    private String askFullName(String name) throws ExitException, IOException {
        String fullName;
        server.send(String.format("Введите Полное название Организации '%s' (нажмите ENTER, если не хотите его указывать): ", name));
        String getName = server.receive().getFirstToken();
        fullName = getName.equals(ENTER_PRESSED_ON_KEYBOARD) ? "": getName.trim();
        if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();
        return fullName;
    }

    private Integer askEmployeesCount(String name) throws ExitException, IOException {
        int value = 0;
        do {
            String message =String.format("Введите количество работников Организации '%s' (нажмите ENTER, если не хотите его указывать): ", name);
            server.send(message);
            String getName = server.receive().getFirstToken();
            String line = getName.equals(ENTER_PRESSED_ON_KEYBOARD) ? "": getName.trim();
            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();

            if (line.isEmpty()) {
                return null;
            } else {
                try {
                    value = Integer.parseInt(line);
                    if (value <= 0) {
                        server.sendErr(VALID_ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException e) {
                    server.sendErr(VALID_ERROR_MESSAGE);
                }
            }
        } while (value <= 0);
        return value;
    }

    private OrganizationType askOrganizationType(String name) throws ExitException, IOException {
//                COMMERCIAL,
//                PUBLIC,
//                OPEN_JOINT_STOCK_COMPANY
        OrganizationType value = null;
        do {
            String message = String.format("Введите тип Организации '%s' (commercial, public, open joint stock company) (нажмите ENTER, если не хотите его указывать): ", name);
            server.send(message);
            String getName = server.receive().getFirstToken();
            String line = getName.equals(ENTER_PRESSED_ON_KEYBOARD) ? "": getName.trim();
            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();
            else if (line.isEmpty())
                return null;
            else
                try {
                    value = OrganizationType.valueOfLabel(line.toLowerCase());
                } catch (IllegalArgumentException e) {
                    server.sendErr(VALID_ERROR_MESSAGE);
                }
        } while(value == null);
        return value;
    }

    private Address askPostalAddress(String name) throws ExitException, IOException {
        String zipCode;
        do {
            String message = String.format("Введите почтовый индекс Организации '%s' (минимум 4 символа): ", name);
            server.send(message);
            zipCode = server.receive().getFirstToken().trim();
            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();
            else if (!zipCode.isEmpty() && zipCode.length() < 4) {
                server.sendErr(VALID_ERROR_MESSAGE);
            }
        } while (zipCode.length() < 4);
        Location location = askLocation(name);

        return new Address(zipCode, location);
    }
    private Location askLocation(String name) throws ExitException, IOException {
//        Integer x; //Поле не может быть null
//        long y;
//        String name; //Поле может быть null
        String locName;
        server.send(String.format("Введите название локации Организации '%s' (нажмите ENTER, если хотите оставить его пустым): ", name));
        String getName = server.receive().getFirstToken();
        locName = getName.equals(ENTER_PRESSED_ON_KEYBOARD) ? "": getName.trim();
        if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();

        Integer x = null;
        do {
            server.send(String.format("Введите значение 'x' локации Организации '%s': ", name));
            String line = server.receive().getFirstToken().trim();

            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    x = Integer.parseInt(line);

                } catch (IllegalArgumentException e) {
                    server.sendErr(VALID_ERROR_MESSAGE);
                }
            }
        } while (x == null);
        long y;
        do {
            server.send(String.format("Введите значение 'y' локации Организации '%s': ", name));
            String line = server.receive().getFirstToken().trim();

            if (server.isCommandReceived(STOP_MULTIPLE_COMMAND)) throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    y = Long.parseLong(line);
                    break;
                } catch (IllegalArgumentException e) {
                    server.sendErr(VALID_ERROR_MESSAGE);
                }
            }
        } while (true);
        return new Location(x, y, locName);
    }
}

package org.example.utils;

import org.example.datalib.console.Console;
import org.example.datalib.exceptions.ExitException;
import org.example.managers.CollectionManager;
import org.example.module.*;

import java.time.LocalDateTime;

/**
 * Этот класс создаёт экземпляр организации из данных, введённых пользователем
 */
public class Asker {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    private Console console;

    public Asker(Console console) {
        this.console = console;
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
    public Organization askOrganization() throws ExitException {
        console.println("Для выполнения команды требуется ввести информацию об Организации");
        console.println("* введите 'exit' для отмены операции *");
        long id = collectionManager.getFreeId();                                //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        String name = askOrgName();                            //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates = askCoordinates(name);                //Поле не может быть null
        LocalDateTime creationDate = LocalDateTime.now();             //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        int annualTurnover = askAnnualTurnover(name);                     //Значение поля должно быть больше 0
        String fullName = askFullName(name);                        //Поле может быть null
        Integer employeesCount = askEmployeesCount(name);                 //Поле может быть null, Значение поля должно быть больше 0
        OrganizationType type = askOrganizationType(name);                  //Поле может быть null
        Address postalAddress = askPostalAddress(name);                  //Поле не может быть null
        return new Organization(id, name, coordinates, creationDate, annualTurnover, fullName, employeesCount, type, postalAddress);
    }

    private String askOrgName() throws ExitException {
        String name;
        do {
            console.print("Введите название Организации: ");
            name = console.readln().trim();
            if (name.equalsIgnoreCase("exit"))
                throw new ExitException();
        } while (name.isEmpty());
        return name;
    }

    private Coordinates askCoordinates(String name) throws ExitException {
//        Integer x; //Максимальное значение поля: 59, Поле не может быть null
//        Double y; //Максимальное значение поля: 115, Поле не может быть null
        Integer x = null;
        do {
            console.print(String.format("Введите координаты \"x\" Организации '%s' (максимальное значение: 59): ", name));
            String line = console.readln().trim();

            if (line.equalsIgnoreCase("exit"))
                throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    x = Integer.parseInt(line);
                    if (x > 59) {
                        x = null;
                        console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                    }
                } catch (IllegalArgumentException e) {
                    console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                }
            }
        } while (x==null);
        Double y = null;
        do {
            console.print(String.format("Введите координаты \"y\" Организации '%s' (максимальное значение: 115): ", name));
            String line = console.readln().trim();

            if (line.equalsIgnoreCase("exit"))
                throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    y = Double.parseDouble(line);
                    if (y > 115) {
                        y = null;
                        console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                    }
                } catch (IllegalArgumentException e) {
                    console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                }
            }
        } while (y==null);
        return new Coordinates(x, y);
    }
    private int askAnnualTurnover(String name) throws ExitException {
        int value = 0;
        do {
            console.print(String.format("Введите значение годового оборота Организации '%s': ", name));
            String line = console.readln().trim();

            if (line.equalsIgnoreCase("exit"))
                throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    value = Integer.parseInt(line);
                    if (value <= 0) {
                        console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                    }
                } catch (IllegalArgumentException e) {
                    console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                }
            }
        } while (value <= 0);
        return value;
    }
    private String askFullName(String name) throws ExitException {
        String fullName;
        console.print(String.format("Введите Полное название Организации '%s' (нажмите ENTER, если не хотите его указывать): ", name));
        fullName = console.readln().trim();
        if (fullName.equalsIgnoreCase("exit"))
            throw new ExitException();
        return fullName;
    }
    private Integer askEmployeesCount(String name) throws ExitException {
        int value = 0;
        do {
            console.print(String.format("Введите количество работников Организации '%s' (нажмите ENTER, если не хотите его указывать): ", name));
            String line = console.readln().trim();
            if (line.equalsIgnoreCase("exit"))
                throw new ExitException();

            if (line.isEmpty()) {
                return null;
            } else {
                try {
                    value = Integer.parseInt(line);
                    if (value <= 0) {
                        console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                    }
                } catch (IllegalArgumentException e) {
                    console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                }
            }
        } while (value <= 0);
        return value;
    }

    private OrganizationType askOrganizationType(String name) throws ExitException {
//                COMMERCIAL,
//                PUBLIC,
//                OPEN_JOINT_STOCK_COMPANY
        OrganizationType value = null;
        do {
            console.print(String.format("Введите тип Организации '%s' (commercial, public, open joint stock company) (нажмите ENTER, если не хотите его указывать): ", name));
            String line = console.readln().trim();
            if (line.equalsIgnoreCase("exit"))
                throw new ExitException();
            else if (line.isEmpty())
                return null;
            else
                try {
                    value = OrganizationType.valueOfLabel(line.toLowerCase());
                } catch (IllegalArgumentException e) {
                    console.printerr("Данное значение не валидно! Попробуйте ещё раз!");
                }
        } while(value == null);
        return value;
    }

    private Address askPostalAddress(String name) throws ExitException {
        String zipCode;
        do {
            console.print(String.format("Введите почтовый индекс Организации '%s' (минимум 4 символа): ", name));
            zipCode = console.readln().trim();
            if (zipCode.equalsIgnoreCase("exit"))
                throw new ExitException();
            else if (!zipCode.isEmpty() && zipCode.length() < 4) {
                console.printerr("Данное значение не валидно! Попробуйте ещё раз!");
            }
        } while (zipCode.length() < 4);
        Location location = askLocation(name);

        return new Address(zipCode, location);
    }
    private Location askLocation(String name) throws ExitException {
//        Integer x; //Поле не может быть null
//        long y;
//        String name; //Поле может быть null
        String locName;
        console.print(String.format("Введите название локации Организации '%s' (нажмите ENTER, если хотите оставить его пустым): ", name));
        locName = console.readln().trim();
        if (locName.equalsIgnoreCase("exit"))
            throw new ExitException();

        Integer x = null;
        do {
            console.print(String.format("Введите значение 'x' локации Организации '%s': ", name));
            String line = console.readln().trim();

            if (line.equalsIgnoreCase("exit"))
                throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    x = Integer.parseInt(line);

                } catch (IllegalArgumentException e) {
                    console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                }
            }
        } while (x == null);
        long y;
        do {
            console.print(String.format("Введите значение 'y' локации Организации '%s': ", name));
            String line = console.readln().trim();

            if (line.equalsIgnoreCase("exit"))
                throw new ExitException();

            if (!line.isEmpty()) {
                try {
                    y = Long.parseLong(line);
                    break;
                } catch (IllegalArgumentException e) {
                    console.printerr("Данное значение не валидно! Попробуйте ещё раз.");
                }
            }
        } while (true);
        return new Location(x, y, name);
    }
}

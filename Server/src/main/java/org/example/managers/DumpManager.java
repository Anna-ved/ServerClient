package org.example.managers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.module.Organization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DumpManager {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // Регистрируем модуль для поддержки LocalDateTime и других классов Java 8 даты/времени
        mapper.registerModule(new JavaTimeModule());
        // Форматирование вывода для красоты
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // Метод для сохранения коллекции организаций в JSON-файл
    public static void saveOrganizations(Collection<Organization> organizations, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), organizations);
    }

    // Метод для загрузки коллекции организаций из JSON-файла
    public static List<Organization> loadOrganizations(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.length() > 0)
            return mapper.readValue(file, new TypeReference<List<Organization>>() {
            });
        return new ArrayList<>();
    }
}

package by.rest.messenger.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ObjectsMerger {

    private ObjectsMerger() {}

    public static <T> Optional<T> merge(T a, T b) {
        T result = null;

        try {
            Class<T> type = (Class<T>) a.getClass();

            List<Field> fieldList = getAllFields(type);
            result = (T) a.getClass().getDeclaredConstructor().newInstance();

            for (Field field : fieldList) {
                String fieldName = field.getName();
                fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                Method getter = findGetter(type, fieldName);
                Method setter = findSetter(type, fieldName);

                Object tempA = getter.invoke(a);
                Object tempB = getter.invoke(b);

                Object parameter = tempB != null ? tempB : tempA;

                setter.invoke(result, parameter);
            }

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(result);
    }

    public static List<Field> getAllFields(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .collect(Collectors.toList());
    }

    public static Method findGetter(Class<?> type, String fileName) {
        return Arrays.stream(type.getDeclaredMethods())
                .filter((m) -> m.getName().contains("get" + fileName))
                .findFirst()
                .orElse(null);
    }

    public static Method findSetter(Class<?> type, String fileName) {
        return Arrays.stream(type.getDeclaredMethods())
                .filter((m) -> m.getName().contains("set" + fileName))
                .findFirst()
                .orElse(null);
    }

}

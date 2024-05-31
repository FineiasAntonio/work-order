package com.workordercontrol.api.Util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class DataUtils {
    public static <T,K> void copyData(T source, K target, String... ignoredFields){
        Field[] sourceFields = source.getClass().getDeclaredFields();
        for (Field sourceField: sourceFields) {
            try {
                Field targetField = target.getClass().getDeclaredField(sourceField.getName());

                if (Arrays.stream(ignoredFields).anyMatch(e -> Objects.equals(e, sourceField.getName()))) {
                    continue;
                };

                sourceField.setAccessible(true);
                targetField.setAccessible(true);

                targetField.set(target, sourceField.get(source));

            } catch (NoSuchFieldException e) {}
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

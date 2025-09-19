package sto.study_plaza.testutil;

import java.lang.reflect.Field;
import java.util.UUID;

public class TestEntityUtil {

    private TestEntityUtil() {}

    public static <T> T setId(T entity, UUID id) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
            return entity;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("ID 필드 주입 실패: " + e.getMessage(), e);
        }
    }

    public static <T> T setRandomId(T entity) {
        return setId(entity, UUID.randomUUID());
    }
}

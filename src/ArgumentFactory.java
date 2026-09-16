import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Создаёт ненулевые значения для параметров методов по их типам.
 * Благодаря этому AnnotationMethodInvoker не знает заранее ни количество,
 * ни конкретные типы параметров вызываемых методов.
 */
public final class ArgumentFactory {
    private ArgumentFactory() {
    }

    public static Object create(Class<?> type) {
        if (type == boolean.class || type == Boolean.class) {
            return true;
        }
        if (type == byte.class || type == Byte.class) {
            return (byte) 1;
        }
        if (type == short.class || type == Short.class) {
            return (short) 2;
        }
        if (type == int.class || type == Integer.class) {
            return 3;
        }
        if (type == long.class || type == Long.class) {
            return 4L;
        }
        if (type == float.class || type == Float.class) {
            return 1.5F;
        }
        if (type == double.class || type == Double.class) {
            return 2.5D;
        }
        if (type == char.class || type == Character.class) {
            return 'A';
        }
        if (type == String.class) {
            return "example";
        }
        if (type.isEnum()) {
            Object[] constants = type.getEnumConstants();
            if (constants.length == 0) {
                throw new IllegalArgumentException("Enum без значений: " + type.getName());
            }
            return constants[0];
        }
        if (type.isArray()) {
            return Array.newInstance(type.getComponentType(), 0);
        }
        if (type == List.class) {
            return new ArrayList<>();
        }
        if (type == Set.class) {
            return new HashSet<>();
        }
        if (type == Map.class) {
            return new HashMap<>();
        }
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            throw new IllegalArgumentException(
                    "Невозможно автоматически создать объект типа " + type.getName()
                            + ": тип является интерфейсом или абстрактным классом"
            );
        }

        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "Для типа " + type.getName()
                            + " нужен конструктор без аргументов или правило в ArgumentFactory",
                    e
            );
        }
    }
}

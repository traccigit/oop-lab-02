import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Находит и вызывает все аннотированные protected/private методы объекта.
 */
public final class AnnotationMethodInvoker {
    private AnnotationMethodInvoker() {
    }

    public static void invokeAnnotatedMethods(Object target) {
        Class<?> targetClass = target.getClass();

        for (Method method : targetClass.getDeclaredMethods()) {
            Repeat repeat = method.getAnnotation(Repeat.class);
            if (repeat == null || !isProtectedOrPrivate(method)) {
                continue;
            }

            Object[] arguments = createArguments(method.getParameterTypes());
            method.setAccessible(true);

            System.out.printf(
                    "%n%s -> %d вызов(а/ов)%n",
                    method.getName(),
                    repeat.value()
            );

            for (int i = 0; i < repeat.value(); i++) {
                try {
                    method.invoke(target, arguments);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(
                            "Не удалось вызвать метод " + method.getName(),
                            e
                    );
                }
            }
        }
    }

    private static boolean isProtectedOrPrivate(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers);
    }

    private static Object[] createArguments(Class<?>[] parameterTypes) {
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            arguments[i] = ArgumentFactory.create(parameterTypes[i]);
        }
        return arguments;
    }
}

public class DemoClass {

    // Публичные методы (2 шт.)
    public void publicMethod(int number) {
        System.out.println("publicMethod: " + number);
    }

    public void printMessage(String message, boolean important) {
        System.out.println("printMessage: " + message + ", important=" + important);
    }

    // Защищённые методы (3 шт.)
    @Repeat(2)
    protected void protectedWithOneArg(String message) {
        System.out.println("protectedWithOneArg: " + message);
    }

    @Repeat(3)
    protected void protectedWithArgs(String text, int number) {
        System.out.println("protectedWithArgs: text=\"" + text + "\", number=" + number);
    }

    protected void protectedNotAnnotated(double value) {
        System.out.println("protectedNotAnnotated: " + value);
    }

    // Приватные методы (3 шт.)
    @Repeat(2)
    private void privateWithPrimitiveArgs(boolean flag, double value, char symbol) {
        System.out.println(
                "privateWithPrimitiveArgs: flag=" + flag
                        + ", value=" + value
                        + ", symbol=" + symbol
        );
    }

    @Repeat(4)
    private void privateWithObjectArg(SampleData data) {
        System.out.println("privateWithObjectArg: " + data);
    }

    private void privateNotAnnotated(long id) {
        System.out.println("privateNotAnnotated: " + id);
    }

    /**
     * Небольшой класс-параметр с конструктором без аргументов.
     * Он нужен, чтобы показать, что вызывающий код умеет передавать
     * не только примитивы и строки, но и реальные объекты (не null).
     */
    public static class SampleData {
        public SampleData() {
        }

        @Override
        public String toString() {
            return "SampleData{}";
        }
    }
}

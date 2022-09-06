import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ComplexExamples {
    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться
            Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        Map<Object, Long> result = Arrays.stream(RAW_DATA)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting()));

        result.forEach((key, value) -> System.out.println("Key: " + key + "\nValue: " + value));

        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */
        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("вывести пару менно в скобках, которые дают сумму - 10");
        System.out.println();

        List<Integer> numbers = List.of(3, 4, 2, 7);

        final String outputFormat = "[%s, %s]";

        AbstractMap.SimpleEntry<Integer, Integer> entry = numbers.stream()
                .flatMap(i -> numbers.stream().map(j -> new AbstractMap.SimpleEntry<>(Math.min(i, j), Math.max(i, j))))
                .filter(e -> e.getKey() + e.getValue() == 10)
                .findFirst()
                .orElse(new AbstractMap.SimpleEntry<>(null, null));
        System.out.printf(outputFormat, entry.getKey(), entry.getValue());

        System.out.println("\n\n**************************************************");
        System.out.println();
        System.out.println("Реализовать функцию нечеткого поиска");
        System.out.println();

        /*
        Task3
            Реализовать функцию нечеткого поиска

                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */

        Stream.of(
                fuzzySearch("car", "ca6$$#_rtwheel"),
                fuzzySearch("cwhl", "cartwheel"),
                fuzzySearch("cwhee", "cartwheel"),
                fuzzySearch("cartwheel", "cartwheel"),
                fuzzySearch("cwheeel", "cartwheel"),
                fuzzySearch("lw", "cartwheel")
        ).forEach(System.out::println);


    }

    private static boolean fuzzySearch(String searchCondition, String searchString) {
        int searchIndex = 0;
        for (int i = 0; i < searchString.length(); i++) {
            if (searchString.charAt(i) == searchCondition.charAt(searchIndex)) {
                searchIndex++;
            }
            if (searchIndex >= searchCondition.length()) {
                return true;
            }
        }
        return false;
    }

}

package lesson2.task2;

import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Stream<Person> personStream = persons.stream();
        long countMinors = personStream.filter(p -> p.getAge() < 18).count();

        personStream = persons.stream();
        List<String> familyRecruit = personStream.filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() < 27)
                .map(Person::getFamily).toList();

        personStream = persons.stream();
        List<Person> workers = personStream.filter(person -> person.getAge() >= 16)
                .filter(person -> (person.getAge() < 65 && person.getSex() == Sex.MAN) ||
                        (person.getAge() < 60 && person.getSex() == Sex.WOMAN))
                .filter(person -> person.getEducation() == Education.HIGHER)
                .sorted(Comparator.comparing(Person::getFamily)).toList();
    }
}

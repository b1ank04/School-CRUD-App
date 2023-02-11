package com.foxminded.school.database;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private static final Random random = new Random();

    private DataGenerator() {
        throw new IllegalStateException("Utility class");
    }
    public static List<Student> createStudents() {
        List<String> firstNames = List.of("Artur", "Bogdan", "Vadim", "Ivan",
                "Dmitriy", "Egor", "Denis", "Anton", "Yaroslav", "Matvei",
                "Evgeniy", "Alla", "Maria", "Anastasia", "Polina", "Alisa",
                "Svetlana", "Julia", "Konstantin", "Victoria");
        List<String> lastNames = List.of("Shurko", "Prokopenko", "Ivanov(a)", "Svetlov(a)",
                "Taran", "Grachov(a)", "Kuzmenko", "Kravchenko", "Popov(a)", "Petrov(a)",
                "Smirnov(a)", "Sokolov(a)", "Golubev(a)", "Orlov(a)", "Antonov(a)", "Pavlov(a)",
                "Fedorov(a)", "Galkin(a)", "Gromov(a)", "Blohin(a)");
        Set<Student> students = new HashSet<>();
        while (students.size() < 200) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            students.add(new Student(null,null, firstName, lastName));
        }

        return assignStudents(students.stream().toList());
    }

    private static List<Student> assignStudents(List<Student> students) {
        for (int i = 1; i <= 10; ++i) {
            int amountOfStudentsInGroup = ThreadLocalRandom.current().nextInt(10, 31);
            for (int j = 0; j < amountOfStudentsInGroup; ++j) {
                students.get(ThreadLocalRandom.current().nextInt(0, 200)).setGroupId(i);
            }
        }
        return students;
    }

    public static Set<String> createGroups() {
        Set<String> groups = new HashSet<>();
        while (groups.size() < 10) {
            char firstChar = (char)(random.nextInt(26) + 'a');
            char secondChar = (char)(random.nextInt(26) + 'a');
            String name = (String.valueOf(firstChar) + secondChar + "-" + (random.nextInt(99-10)+10)).toUpperCase();
            groups.add(name);
        }
        return groups;
    }

    public static Set<Course> createCourses() {
        List<String> courses = new ArrayList<>(List.of("Mathematics", "Biology", "Physical Education",
                "Art", "Astronomy", "Physics", "Computer Science",
                "History", "Economics", "Philosophy"));
        Collections.shuffle(courses);
        Set<Course> myCourses = new HashSet<>();
        for (String name : courses) {
            myCourses.add(new Course(null, name, null));
        }
        return myCourses;
    }

    public static Map<Integer, Set<Integer>> assignStudentsToCourses() {
        Map<Integer, Set<Integer>> studCourse = new HashMap<>();
        for (int i = 1; i <= 200; ++i) {
            int count = ThreadLocalRandom.current().nextInt(1,4);
            Set<Integer> courses = new HashSet<>();
            while (courses.size() < count){
                courses.add(ThreadLocalRandom.current().nextInt(1,11));
            }
            studCourse.put(i, courses);
        }
        return studCourse;
    }
}

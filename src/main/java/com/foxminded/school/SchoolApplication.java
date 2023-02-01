package com.foxminded.school;

import com.foxminded.school.implementations.StudentDaoImpl;
import com.foxminded.school.model.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SchoolApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StudentDaoImpl studentDao = new StudentDaoImpl();
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("""
                    Please choose the method you want to use:
                    save
                    find
                    findAll
                    delete""");
            String input = sc.next();
            List<Integer> groupRange = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            switch(input) {
                case ("save") -> {
                    System.out.println("Student ID (leave clear to create a new");
                    String idString = sc.next();
                    System.out.println("Group ID (leave clear if don't has a group:");
                    String groupIdString = sc.next();

                    Long id = null;
                    Integer groupId = null;
                    try {
                        id = Long.parseLong(idString);
                        groupId = Integer.parseInt(groupIdString);
                    }
                    catch (Exception ignored){}
                    System.out.println("First name:");
                    String firstName = sc.next();
                    System.out.println("Last name:");
                    String lastName = sc.next();
                    Student student = new Student(id, groupId, firstName, lastName);
                    Student result = studentDao.save(student);
                    System.out.println(result.toString() + " -saved");
                }
                case ("find") -> {
                    System.out.println("ID:");
                    Long id = sc.nextLong();
                    Optional<Student> student = studentDao.findById(id);
                    System.out.println(student.toString());
                }
                case ("findAll") -> {
                    List<Student> students = studentDao.findAll();
                    for (Student s : students) {
                        System.out.println(s.toString());
                    }
                }
                case ("delete") -> {
                    System.out.println("ID:");
                    Long id = sc.nextLong();
                    Optional<Student> student = studentDao.findById(id);
                    studentDao.deleteById(id);
                    System.out.println(student.toString()+" -deleted");
                }
                default -> System.out.println("Wrong method");
            }
        }
    }
}

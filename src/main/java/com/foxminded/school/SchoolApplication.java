package com.foxminded.school;

import com.foxminded.school.dao.jdbc.JDBCStudentDao;
import com.foxminded.school.model.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SchoolApplication implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        JDBCStudentDao studentDao = new JDBCStudentDao(new JdbcTemplate(dataSource));
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("""
                    Please choose the method you want to use:
                    save
                    find
                    findAll
                    delete""");
            String input = sc.next();
            switch(input) {
                case ("save") -> {
                    System.out.println("Student ID (type 0 to create a new)");
                    long id = sc.nextLong();
                    System.out.println("Group ID (type 0 if don't has a group):");
                    int groupId = sc.nextInt();
                    System.out.println("First name:");
                    String firstName = sc.next();
                    System.out.println("Last name:");
                    String lastName = sc.next();
                    Student student = new Student(id == 0? null:id, groupId == 0? null:groupId, firstName, lastName);
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

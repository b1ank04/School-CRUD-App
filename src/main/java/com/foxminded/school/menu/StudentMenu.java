package com.foxminded.school.menu;

import com.foxminded.school.dao.jdbc.JDBCStudentDao;
import com.foxminded.school.model.student.Student;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentMenu {

    private StudentMenu() {
        throw new IllegalStateException("Utility class");
    }
    public static void run(JdbcTemplate jdbcTemplate, Logger logger) throws SQLException {
        JDBCStudentDao jdbcStudentDao = new JDBCStudentDao(jdbcTemplate);

        try (Scanner sc = new Scanner(System.in)) {
            logger.info("""
                    
                    Please choose the method you want to use:
                    save
                    find
                    findAll
                    delete""");
            String method = sc.next();
            switch(method) {
                case ("save") -> {
                    logger.info("Student ID (type 0 to create a new)");
                    long id = sc.nextLong();
                    logger.info("Group ID (type 0 if don't has a group):");
                    int groupId = sc.nextInt();
                    logger.info("First name:");
                    String firstName = sc.next();
                    logger.info("Last name:");
                    String lastName = sc.next();
                    Student student = new Student(id == 0? null:id, groupId == 0? null:groupId, firstName, lastName);
                    Student result = jdbcStudentDao.save(student);
                    logger.info("{} -saved",result);
                }
                case ("find") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Student> student = jdbcStudentDao.findById(id);
                    if (student.isPresent()) {
                        logger.info("{}",student.get());
                    }
                    else {
                        logger.error("Student doesn't exist");
                    }
                }
                case ("findAll") -> {
                    List<Student> students = jdbcStudentDao.findAll();
                    for (Student s : students) {
                        logger.info("{}",s);
                    }
                }
                case ("delete") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Student> student = jdbcStudentDao.findById(id);
                    jdbcStudentDao.deleteById(id);
                    logger.info("{} -deleted",student);
                }
                default -> logger.error("Wrong method");
            }
        }
    }
}

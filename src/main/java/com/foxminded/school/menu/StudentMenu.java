package com.foxminded.school.menu;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import com.foxminded.school.service.StudentService;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentMenu {

    private StudentMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(StudentService studentService, Logger logger) throws SQLException {


        try (Scanner sc = new Scanner(System.in)) {
            logger.info("""
                    
                    Please choose the method you want to use:
                    save
                    find
                    findAll
                    delete
                    findRelatedCourses
                    addCourse
                    deleteCourse""");
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
                    Student result = studentService.save(student);
                    logger.info("{} -saved",result);
                }
                case ("find") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Student> student = studentService.findById(id);
                    if (student.isPresent()) {
                        logger.info("{}",student.get());
                    }
                    else {
                        logger.error("Student doesn't exist");
                    }
                }
                case ("findAll") -> {
                    List<Student> students = studentService.findAll();
                    for (Student s : students) {
                        logger.info("{}",s);
                    }
                }
                case ("delete") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Student> student = studentService.findById(id);
                    studentService.deleteById(id);
                    logger.info("{} -deleted",student);
                }
                case ("findRelatedCourses") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    for (Course c : studentService.findRelatedCourses(id)) {
                        logger.info("{}", c);
                    }
                }
                case ("addCourse") -> {
                    logger.info("Student ID:");
                    Long studentId = sc.nextLong();
                    logger.info("Course ID:");
                    Long courseId = sc.nextLong();
                    studentService.addCourse(studentId, courseId);
                    logger.info("Successfully added");
                }
                case ("deleteCourse") -> {
                    logger.info("Student ID:");
                    Long studentId = sc.nextLong();
                    logger.info("Course ID:");
                    Long courseId = sc.nextLong();
                    studentService.deleteCourse(studentId, courseId);
                    logger.info("Successfully deleted");
                }
                default -> logger.error("Wrong method");
            }
        }
    }
}

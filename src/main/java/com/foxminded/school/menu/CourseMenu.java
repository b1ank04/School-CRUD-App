package com.foxminded.school.menu;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import com.foxminded.school.service.CourseService;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CourseMenu {

    private CourseMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(CourseService courseService, Logger logger) throws SQLException {
        try (Scanner sc = new Scanner(System.in)) {
            logger.info("""
                                        
                    Please choose the method you want to use:
                    save
                    find
                    findAll
                    delete
                    findRelatedStudents""");
            String method = sc.next();
            switch (method) {
                case ("save") -> {
                    logger.info("Course ID (type 0 to create new)");
                    long id = sc.nextLong();
                    logger.info("Name:");
                    String name = sc.next();
                    logger.info("Description: ");
                    String description = sc.next();
                    Course course = new Course(id == 0 ? null : id, name, description.equals("") ? null : description);
                    logger.info("{} -saved", courseService.save(course));
                }
                case ("find") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Course> course = courseService.findById(id);
                    if (course.isPresent()) {
                        logger.info("{}",course.get());
                    }
                    else {
                        logger.error("Course doesn't exist");
                    }
                }
                case ("findAll") -> {
                    List<Course> courses = courseService.findAll();
                    for (Course c : courses) {
                        logger.info("{}",c);
                    }
                }
                case ("delete") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Course> course = courseService.findById(id);
                    courseService.deleteById(id);
                    logger.info("{} -deleted",course);
                }
                case ("findRelatedStudents") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    for (Student student : courseService.findRelatedStudents(id)) {
                        logger.info("{}", student);
                    }
                }
                default -> logger.error("Wrong method");
            }
        }
    }
}

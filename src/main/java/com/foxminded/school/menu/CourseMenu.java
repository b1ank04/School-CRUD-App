package com.foxminded.school.menu;

import com.foxminded.school.dao.jdbc.JDBCCourseDao;
import com.foxminded.school.model.course.Course;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CourseMenu {

    private CourseMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(JdbcTemplate jdbcTemplate, Logger logger) throws SQLException {
        JDBCCourseDao jdbcCourseDao = new JDBCCourseDao(jdbcTemplate);
        try (Scanner sc = new Scanner(System.in)) {
            logger.info("""
                                        
                    Please choose the method you want to use:
                    save
                    find
                    findAll
                    delete""");
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
                    logger.info("{} -saved", jdbcCourseDao.save(course));
                }
                case ("find") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Course> course = jdbcCourseDao.findById(id);
                    if (course.isPresent()) {
                        logger.info("{}",course.get());
                    }
                    else {
                        logger.error("Course doesn't exist");
                    }
                }
                case ("findAll") -> {
                    List<Course> courses = jdbcCourseDao.findAll();
                    for (Course c : courses) {
                        logger.info("{}",c);
                    }
                }
                case ("delete") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Course> course = jdbcCourseDao.findById(id);
                    jdbcCourseDao.deleteById(id);
                    logger.info("{} -deleted",course);
                }
                default -> logger.error("Wrong method");
            }
        }
    }
}

package com.foxminded.school.menu;

import com.foxminded.school.dao.jdbc.JDBCGroupDao;
import com.foxminded.school.model.group.Group;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GroupMenu {

    private GroupMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(JdbcTemplate jdbcTemplate, Logger logger) throws SQLException {
        JDBCGroupDao jdbcGroupDao = new JDBCGroupDao(jdbcTemplate);
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
                    logger.info("Group ID (type 0 to create new)");
                    long id = sc.nextLong();
                    logger.info("Name:");
                    String name = sc.next();
                    Group course = new Group(id == 0 ? null : id, name);
                    logger.info("{} -saved", jdbcGroupDao.save(course));
                }
                case ("find") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Group> group = jdbcGroupDao.findById(id);
                    if (group.isPresent()) {
                        logger.info("{}",group.get());
                    }
                    else {
                        logger.error("Group doesn't exist");
                    }
                }
                case ("findAll") -> {
                    List<Group> groups = jdbcGroupDao.findAll();
                    for (Group g : groups) {
                        logger.info("{}", g);
                    }
                }
                case ("delete") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Group> group = jdbcGroupDao.findById(id);
                    jdbcGroupDao.deleteById(id);
                    logger.info("{} -deleted",group);
                }
                default -> logger.error("Wrong method");
            }
        }
    }
}

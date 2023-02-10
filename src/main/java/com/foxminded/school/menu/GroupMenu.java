package com.foxminded.school.menu;

import com.foxminded.school.model.group.Group;
import com.foxminded.school.service.GroupService;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GroupMenu {

    private GroupMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(GroupService groupService, Logger logger) throws SQLException {
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
                    logger.info("{} -saved", groupService.save(course));
                }
                case ("find") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Group> group = groupService.findById(id);
                    if (group.isPresent()) {
                        logger.info("{}",group.get());
                    }
                    else {
                        logger.error("Group doesn't exist");
                    }
                }
                case ("findAll") -> {
                    List<Group> groups = groupService.findAll();
                    for (Group g : groups) {
                        logger.info("{}", g);
                    }
                }
                case ("delete") -> {
                    logger.info("ID:");
                    Long id = sc.nextLong();
                    Optional<Group> group = groupService.findById(id);
                    groupService.deleteById(id);
                    logger.info("{} -deleted",group);
                }
                default -> logger.error("Wrong method");
            }
        }
    }
}

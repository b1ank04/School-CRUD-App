package com.foxminded.school.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("!test")
public class ConsoleMenu implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleMenu.class);

    public ConsoleMenu(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            LOG.info("""
                    Choose dao:
                    student
                    course
                    group""");
            String daoName = sc.nextLine();
            switch (daoName) {
                case ("student") -> StudentMenu.run(jdbcTemplate, LOG);
                case ("course") -> CourseMenu.run(jdbcTemplate, LOG);
                case ("group") -> GroupMenu.run(jdbcTemplate, LOG);
                default -> LOG.error("Wrong dao");
            }
        }
    }
}

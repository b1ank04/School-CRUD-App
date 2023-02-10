package com.foxminded.school.menu;

import com.foxminded.school.service.CourseService;
import com.foxminded.school.service.GroupService;
import com.foxminded.school.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("!test")
public class ConsoleMenu implements ApplicationRunner {
    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleMenu.class);

    public ConsoleMenu(StudentService studentService, GroupService groupService, CourseService courseService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.courseService = courseService;
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
                case ("student") -> StudentMenu.run(studentService, LOG);
                case ("course") -> CourseMenu.run(courseService, LOG);
                case ("group") -> GroupMenu.run(groupService, LOG);
                default -> LOG.error("Wrong dao");
            }
        }
    }
}

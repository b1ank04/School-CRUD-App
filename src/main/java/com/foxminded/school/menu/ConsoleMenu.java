package com.foxminded.school.menu;

import com.foxminded.school.service.CourseService;
import com.foxminded.school.service.GroupService;
import com.foxminded.school.service.StudentService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class ConsoleMenu implements ApplicationRunner {
    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;

    public ConsoleMenu(StudentService studentService, GroupService groupService, CourseService courseService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.courseService = courseService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        DefaultConsole console = new DefaultConsole();
        console.println("""
                Choose dao:
                student
                course
                group""");
        String daoName = console.readString();
        switch (daoName) {
            case ("student") -> StudentMenu.run(studentService);
            case ("course") -> CourseMenu.run(courseService);
            case ("group") -> GroupMenu.run(groupService);
            default -> console.println("Wrong dao");
        }
    }
}


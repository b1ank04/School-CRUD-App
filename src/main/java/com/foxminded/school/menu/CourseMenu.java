package com.foxminded.school.menu;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import com.foxminded.school.service.CourseService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CourseMenu {

    private CourseMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(CourseService courseService) throws SQLException {
        DefaultConsole console = new DefaultConsole();
        console.println("""                    
                Please choose the method you want to use:
                save
                find
                findAll
                delete
                findRelatedStudents""");
        String method = console.readString();
        switch (method) {
            case ("save") -> {
                console.println("Course ID (type 0 to create new)");
                long id = Long.parseLong(console.readString());
                console.println("Name:");
                String name = console.readString();
                console.println("Description: ");
                String description = console.readString();
                Course course = new Course(id == 0 ? null : id, name, description.equals("") ? null : description);
                console.println(courseService.save(course).toString());
            }
            case ("find") -> {
                console.println("ID:");
                Long id = Long.parseLong(console.readString());
                Optional<Course> course = courseService.findById(id);
                if (course.isPresent()) {
                    console.println(course.get().toString());
                }
                else {
                    console.println("Course doesn't exist");
                }
            }
            case ("findAll") -> {
                List<Course> courses = courseService.findAll();
                for (Course c : courses) {
                    console.println(c.toString());
                }
            }
            case ("delete") -> {
                console.println("ID:");
                Long id = Long.parseLong(console.readString());
                Optional<Course> course = courseService.findById(id);
                courseService.deleteById(id);
                console.println(course.toString());
            }
            case ("findRelatedStudents") -> {
                console.println("ID:");
                Long id = Long.parseLong(console.readString());
                for (Student student : courseService.findRelatedStudents(id)) {
                    console.println(student.toString());
                }
            }
            default -> console.println("Wrong method");
        }
    }
}

package com.foxminded.school.menu;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import com.foxminded.school.service.StudentService;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class StudentMenu implements SubMenu {

    private final StudentService studentService;
    private final DefaultConsole console;

    public StudentMenu(StudentService studentService, DefaultConsole console) {
        this.studentService = studentService;
        this.console = console;
    }

    public void run() throws SQLException {
        console.println("""
                Please choose the method you want to use:
                save
                find
                findAll
                delete
                findRelatedCourses
                addCourse
                deleteCourse""");
        String method = console.readString();
        switch(method) {
            case ("save") -> {
                console.println("Student ID (type 0 to create a new)");
                long id = Long.parseLong(console.readString());
                console.println("Group ID (type 0 if don't has a group):");
                int groupId = Integer.parseInt(console.readString());
                console.println("First name:");
                String firstName = console.readString();
                console.println("Last name:");
                String lastName = console.readString();
                Student student = new Student(id == 0? null:id, groupId == 0? null:groupId, firstName, lastName);
                Student result = studentService.save(student);
                console.println(result.toString() + " -saved");
            }
            case ("find") -> {
                console.print("ID:");
                Long id = Long.parseLong(console.readString());
                Optional<Student> student = studentService.findById(id);
                if (student.isPresent()) {
                    console.println(student.get().toString());
                }
                else {
                    console.println("Student doesn't exist");
                }
            }
            case ("findAll") -> {
                List<Student> students = studentService.findAll();
                for (Student s : students) {
                    console.println(s.toString());
                }
            }
            case ("delete") -> {
                console.println("ID:");
                Long id = Long.parseLong(console.readString());
                Optional<Student> student = studentService.findById(id);
                studentService.deleteById(id);
                console.println(student.toString());
            }
            case ("findRelatedCourses") -> {
                console.println("ID:");
                Long id = Long.parseLong(console.readString());
                for (Course c : studentService.findRelatedCourses(id)) {
                    console.println(c.toString());
                }
            }
            case ("addCourse") -> {
                console.println("Student ID:");
                Long studentId = Long.parseLong(console.readString());
                console.println("Course ID:");
                Long courseId = Long.parseLong(console.readString());
                studentService.addCourse(studentId, courseId);
                console.println("Successfully added");
            }
            case ("deleteCourse") -> {
                console.println("Student ID:");
                Long studentId = Long.parseLong(console.readString());
                console.println("Course ID:");
                Long courseId = Long.parseLong(console.readString());
                studentService.removeCourse(studentId, courseId);
                console.println("Successfully deleted");
            }
            default -> console.println("Wrong method");
        }
    }
    public String getTitle() {
        return "student";
    }
}

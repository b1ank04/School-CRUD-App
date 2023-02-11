package com.foxminded.school.menu;

import com.foxminded.school.model.group.Group;
import com.foxminded.school.service.GroupService;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class GroupMenu implements SubMenu {

    private final GroupService groupService;
    private final Console console;

    public GroupMenu(GroupService groupService, Console console) {
        this.groupService = groupService;
        this.console = console;
    }

    public void run() throws SQLException {
        console.println("""                    
                Please choose the method you want to use:
                save
                find
                findAll
                delete""");
        String method = console.readString();
        switch (method) {
            case ("save") -> {
                console.println("Group ID (type 0 to create new)");
                long id = Long.parseLong(console.readString());
                console.println("Name:");
                String name = console.readString();
                Group course = new Group(id == 0 ? null : id, name);
                console.println(groupService.save(course).toString());
            }
            case ("find") -> {
                console.println("ID:");
                Long id = Long.parseLong(console.readString());
                Optional<Group> group = groupService.findById(id);
                if (group.isPresent()) {
                    console.println(group.get().toString());
                }
                else {
                    console.println("Group doesn't exist");
                }
            }
            case ("findAll") -> {
                List<Group> groups = groupService.findAll();
                for (Group g : groups) {
                    console.println(g.toString());
                }
            }
            case ("delete") -> {
                console.println("ID:");
                Long id = Long.parseLong(console.readString());
                Optional<Group> group = groupService.findById(id);
                groupService.deleteById(id);
                console.println(group.toString());
            }
            default -> console.println("Wrong method");
        }
    }
    public String getTitle() {
        return "group";
    }
}

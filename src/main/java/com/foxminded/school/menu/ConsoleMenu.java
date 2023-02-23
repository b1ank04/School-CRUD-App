package com.foxminded.school.menu;

import com.foxminded.school.database.DatabaseService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Profile("!test")
public class ConsoleMenu implements ApplicationRunner {

    private final Console console;
    private final List<SubMenu> subMenus;
    private final DatabaseService databaseService;

    public ConsoleMenu(Console console, List<SubMenu> subMenus, DatabaseService databaseService) {
        this.console = console;
        this.subMenus = subMenus;
        this.databaseService = databaseService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        databaseService.execute();
        while (true) {
            console.println("Choose dao:");
            subMenus.forEach(menu -> console.println(menu.getTitle()));
            console.println("exit (to leave)");
            String daoName = console.readString();
            if (daoName.equals("exit")) return;
            Optional<SubMenu> menu = subMenus.stream().filter(it -> it.getTitle().equals(daoName)).findFirst();
            if (menu.isPresent()) menu.get().run();
            else console.println("Wrong dao");
        }
    }
}


package com.foxminded.school.menu;

import java.sql.SQLException;

public interface SubMenu {

    void run() throws SQLException;
    String getTitle();
}

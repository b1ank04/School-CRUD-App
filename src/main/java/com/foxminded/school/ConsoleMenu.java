package com.foxminded.school;

import com.foxminded.school.dao.jdbc.JDBCStudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMenu implements ApplicationRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleMenu.class);
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JDBCStudentDao jdbcStudentDao = new JDBCStudentDao(jdbcTemplate);
        jdbcStudentDao.deleteCourse(100L, 1L);
        jdbcStudentDao.findRelatedCourses(100L).forEach(System.out::println);
    }
}

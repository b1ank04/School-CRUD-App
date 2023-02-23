package com.foxminded.school.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorTest {

    @Test
    void shouldCreateTwoHundredStudents() {
        assertEquals(200, DataGenerator.createStudents().size());
    }

    @Test
    void shouldCreateTenCourses() {
        assertEquals(10, DataGenerator.createCourses().size());
    }

    @Test
    void shouldCreateTenGroups() {
        assertEquals(10, DataGenerator.createCourses().size());
    }
}
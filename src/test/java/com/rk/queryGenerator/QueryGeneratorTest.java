package com.rk.queryGenerator;

import com.rk.domain.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryGeneratorTest {
    QueryGenerator queryGenerator = new QueryGenerator();
    Entity entity;
    String actualQuery;
    String expectedQuery;

    @BeforeEach
    void setUp() {
        entity = new Entity();
        entity.setId(1L);
        entity.setFirstName("Roman");
        entity.setLastName("Not save");
        entity.setSalary(2000.5);
    }

    @Test
    @DisplayName("return all entity from DB")
    void getAll() {
        actualQuery = queryGenerator.getAll(Entity.class);
        expectedQuery = "SELECT id, firstName, salary FROM entity";

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("insert new entry to DB")
    void insert() throws IllegalAccessException {
        actualQuery = queryGenerator.insert(entity);
        expectedQuery = "INSERT INTO entity (firstName, salary) VALUES ('Roman', 2000.5)";

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("insert null to DB throw exception")
    void insertNull(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> queryGenerator.insert(null));
        assertEquals(exception.getMessage(), "Trying to pass null to a method as an argument");
    }

    @Test
    @DisplayName("insert new entry to DB, if one field is null")
    void insert_OneFieldNull() throws IllegalAccessException {
        entity.setFirstName(null);
        actualQuery = queryGenerator.insert(entity);
        expectedQuery = "INSERT INTO entity (firstName, salary) VALUES (null, 2000.5)";

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("insert new entry to DB, if entity has only one field")
    void insert_OneField() throws IllegalAccessException {
        entity.setFirstName(null);
        actualQuery = queryGenerator.insert(entity);
        expectedQuery = "INSERT INTO entity (salary) VALUES (2000.5)";

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("update entity in DB")
    void update() throws IllegalAccessException {
        actualQuery = queryGenerator.update(entity);
        expectedQuery = "UPDATE entity SET firstName = 'Roman', salary = 2000.5 WHERE id = 1";

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("update entity in DB,  if one field is null")
    void update_OneFieldNull() throws IllegalAccessException {
        entity.setFirstName(null);
        actualQuery = queryGenerator.update(entity);
        expectedQuery = "UPDATE entity SET salary = 2000.5 WHERE id = 1";

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("update with argument null throw exception")
    void updateNull(){
        Exception exception = assertThrows(IllegalArgumentException.class,() -> queryGenerator.insert(null));
        assertEquals(exception.getMessage(), "Trying to pass null to a method as an argument");
    }

    @Test
    @DisplayName("delete entity in DB,  if one field is null")
    void delete() throws IllegalAccessException {
        actualQuery = queryGenerator.delete(entity);
        expectedQuery = "DELETE FROM entity WHERE id = 1";

        assertEquals(expectedQuery, actualQuery);
    }
}
package com.rk.queryGenerator;

import com.rk.domain.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void getAll() {
       actualQuery = queryGenerator.getAll(Entity.class);
       expectedQuery = "SELECT id, firstName, salary FROM entity";

       assertEquals(expectedQuery,actualQuery);
    }

    @Test
    void insert() {
        actualQuery = queryGenerator.insert(entity);
        expectedQuery = "INSERT INTO entity (id, firstName, salary) VALUES ('1', 'Roman', 2000.5)";

        assertEquals(expectedQuery,actualQuery);
    }

    @Test
    void update() {
        actualQuery = queryGenerator.update(entity);
        expectedQuery = "UPDATE entity SET id = 1, firstName = 'Roma', salary = 2000.5 WHERE id = 1";

        assertEquals(expectedQuery,actualQuery);
    }

    @Test
    void delete() {
        actualQuery = queryGenerator.update(entity);
        expectedQuery = "DELETE FROM entity WHERE id = 1;";

        assertEquals(expectedQuery,actualQuery);
    }
}
package com.rk.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StarterTest {

    @Test
    void start() throws InvocationTargetException, IllegalAccessException {
        AppTest appTest = new AppTest();
        Starter starter = new Starter();
        starter.run(appTest);

        assertEquals(10, appTest.getNumber());
        assertEquals("worked", appTest.getStringPrivate());
        assertNull(appTest.getStringPublic());
    }
}
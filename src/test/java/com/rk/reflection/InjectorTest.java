package com.rk.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {

    @Test
    @DisplayName("inject value in fields")
    void injectFields() throws Exception {
        Injector injector = new Injector();
        AppTest appTest = new AppTest();
        injector.InjectFields(appTest);

        assertEquals(0, appTest.getNumber());
        assertNull(appTest.getStringPrivate());

        assertNotNull(appTest.getStringPublic());
        assertNotNull(appTest.getStringList());

    }
}
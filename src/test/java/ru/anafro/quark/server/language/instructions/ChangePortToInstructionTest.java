package ru.anafro.quark.server.language.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.anafro.quark.server.facade.Quark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.anafro.quark.server.facade.Quark.query;

class ChangePortToInstructionTest {
    @BeforeEach
    public void setUp() {
        Quark.runServicesAsynchronously();
    }

    @AfterEach
    public void tearDown() {
        Quark.server().stop();
    }

    @Test
    @DisplayName("Should change port")
    public void shouldChangePort() {
        // When
        query("change port to 12345;");

        // Then
        assertEquals(12345, Quark.server().getPort());
    }
}
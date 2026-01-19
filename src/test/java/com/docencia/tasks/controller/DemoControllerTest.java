package com.docencia.tasks.controller;

import com.docencia.tasks.infrastructure.security.JwtService;
import com.docencia.tasks.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoController.class)
@Import(SecurityConfig.class) // Importar configuración de seguridad para que se aplique
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService; // Se necesita porque SecurityConfig lo usa

    @Test
    @WithMockUser // Simula un usuario autenticado
    void testDemoAuthenticated() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(content().string("¡Hola! Si ves esto es que estás autenticado correctamente."));
    }

    @Test
    void testDemoUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isForbidden()); // Sin usuario, debería ser prohibido (o unauthorized dependiendo
                                                    // del config)
    }
}

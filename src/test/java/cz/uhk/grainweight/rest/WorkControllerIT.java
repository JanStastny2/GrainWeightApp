package cz.uhk.grainweight.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WorkControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void listRecords_Serial_ShouldReturnApiResponseWithTimingFields() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/work/records")
                        .param("mode", "SERIAL"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.durationMs").isNumber())
                .andExpect(jsonPath("$.serverProcessingMs").isNumber())
                .andExpect(jsonPath("$.queueWaitMs").isNumber())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertNotNull(body);
        assertTrue(body.contains("serverProcessingMs"), "Response should contain serverProcessingMs field");
        assertTrue(body.contains("queueWaitMs"), "Response should contain queueWaitMs field");
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void listRecords_Pool_ShouldReturnApiResponseWithTimingFields() throws Exception {
        mockMvc.perform(get("/api/work/records")
                        .param("mode", "POOL")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.serverProcessingMs").isNumber())
                .andExpect(jsonPath("$.queueWaitMs").isNumber());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void listRecords_Virtual_ShouldReturnApiResponseWithTimingFields() throws Exception {
        mockMvc.perform(get("/api/work/records")
                        .param("mode", "VIRTUAL")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.serverProcessingMs").isNumber())
                .andExpect(jsonPath("$.queueWaitMs").isNumber());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void listRecords_ShouldContain_DataArray() throws Exception {
        mockMvc.perform(get("/api/work/records")
                        .param("mode", "SERIAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void listRecords_WithDelay_ShouldReturnSuccessfully() throws Exception {
        mockMvc.perform(get("/api/work/records")
                        .param("mode", "SERIAL")
                        .param("delayMs", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void listRecords_WithoutAuth_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/work/records")
                        .param("mode", "SERIAL"))
                .andExpect(status().isOk());
    }
}

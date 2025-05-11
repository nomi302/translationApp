package com.translation.assessment;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.translation.assessment.dto.TranslationRequest;
import com.translation.assessment.dto.TranslationResponse;
import com.translation.assessment.exception.ResourceNotFoundException;
import com.translation.assessment.service.TranslationService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslationService translationService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String API_KEY_HEADER = "X-API-KEY";
    private final String TEST_API_KEY = "test-key";

    private final TranslationResponse mockResponse = new TranslationResponse(
            1L, "welcome.header", "en", "Welcome", Set.of("greeting", "header")
    );

    // Helper method for debugging responses
    private void debugResponse(MvcResult result) throws Exception {
        System.out.println("Response Body: " + result.getResponse().getContentAsString());
    }

    @Test
    void createTranslation_WithInvalidInput_ShouldReturnBadRequest() throws Exception {
        TranslationRequest invalidRequest = new TranslationRequest(
                "",  // Invalid blank key
                "",  // Invalid blank locale
                "",  // Invalid blank content
                Collections.emptySet()  // Invalid empty tags
        );

        mockMvc.perform(post("/api/translations/")
                        .header(API_KEY_HEADER, TEST_API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTranslation_ShouldReturnOk() throws Exception {
        when(translationService.getTranslation(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/translations/1")
                        .header(API_KEY_HEADER, TEST_API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("Welcome"));
    }

    @Test
    void getTranslation_WithInvalidId_ShouldReturnNotFound() throws Exception {
        when(translationService.getTranslation(999L))
                .thenThrow(new ResourceNotFoundException("Translation not found"));

        mockMvc.perform(get("/api/translations/999")
                        .header(API_KEY_HEADER, TEST_API_KEY))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteTranslation_ShouldReturnNoContent() throws Exception {
        doNothing().when(translationService).deleteTranslation(1L);

        mockMvc.perform(delete("/api/translations/1")
                        .header(API_KEY_HEADER, TEST_API_KEY))
                .andExpect(status().isNoContent());
    }


    @Test
    void exportTranslations_ShouldReturnCorrectStructure() throws Exception {
        Map<String, Map<String, String>> exportData = Map.of(
                "en", Map.of("welcome_header", "Welcome"),  // Use underscore format
                "es", Map.of("welcome_header", "Bienvenido")
        );

        when(translationService.exportTranslations()).thenReturn(exportData);

        MvcResult result = mockMvc.perform(get("/api/translations/export")
                        .header(API_KEY_HEADER, TEST_API_KEY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.en.welcome_header").value("Welcome"))  // Match underscore format
                .andExpect(jsonPath("$.es.welcome_header").value("Bienvenido"))
                .andReturn();

        debugResponse(result);  // Debug output
    }
}
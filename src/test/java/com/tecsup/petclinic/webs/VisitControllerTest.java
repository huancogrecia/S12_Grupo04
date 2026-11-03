package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("h2")
class VisitControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindAllVisits() throws Exception {
        mockMvc.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(6))));
    }

    @Test
    void testFindVisitOK() throws Exception {
        mockMvc.perform(get("/visits/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("rabies shot")));
    }

    @Test
    void testFindVisitKO() throws Exception {
        mockMvc.perform(get("/visits/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateVisit() throws Exception {
        mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(visitRequestJson("Control general")))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("Control general")))
                .andExpect(jsonPath("$.visitDate", is("2026-11-03")));
    }

    @Test
    void testUpdateVisit() throws Exception {
        String createResponse = mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(visitRequestJson("Control antes de actualizar")))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int visitId = OBJECT_MAPPER.readTree(createResponse).get("id").asInt();

        mockMvc.perform(put("/visits/{id}", visitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(visitRequestJson("Control actualizado")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(visitId)))
                .andExpect(jsonPath("$.description", is("Control actualizado")))
                .andExpect(jsonPath("$.visitDate", is("2026-11-03")));
    }

    @Test
    void testDeleteVisit() throws Exception {
        String createResponse = mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(visitRequestJson("Visita para eliminar")))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int visitId = OBJECT_MAPPER.readTree(createResponse).get("id").asInt();

        mockMvc.perform(delete("/visits/{id}", visitId))
                .andExpect(status().isOk());
    }

    private String visitRequestJson(String description) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(new VisitRequest(
                "2026-11-03",
                description,
                new PetReference(1)
        ));
    }

    private record VisitRequest(String visitDate, String description, PetReference pet) {
    }

    private record PetReference(Integer id) {
    }
}

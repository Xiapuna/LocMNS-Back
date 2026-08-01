package com.mns.cda.locmnsback.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mns.cda.locmnsback.dto.EquipmentCreateDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RequiredArgsConstructor
class LocMnsBackApplicationTest {

    private final WebApplicationContext context;

    private final ObjectMapper mapper = JsonMapper.builder().build();
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        // Configuration de MockMvc avec la gestion de Spring Security
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // Teste l'accès anonyme (doit être refusé : 403 FORBIDDEN)
    @Test
    public void callEquipmentListAsAnonymous_shouldReturnCode403() throws Exception {
        mvc.perform(get("/equipment/list"))
                .andExpect(status().isForbidden());
    }

    // Teste l'accès avec un rôle USER classique (200 OK)
    @Test
    @WithMockUser(roles = {"USER"})
    public void callEquipmentListAsUser_shouldReturnCode200() throws Exception {
        mvc.perform(get("/equipment/list"))
                .andExpect(status().isOk());
    }

    // Teste la création avec un utilisateur authentifié spécifique
    @Test
    @WithUserDetails("celia.godfrin@gmail.com")
    public void callCreateEquipmentAsAdmin_shouldReturnCode201() throws Exception {
        EquipmentCreateDto dto = new EquipmentCreateDto("Écran Dell 27", 1, 1, 1);
        String jsonEquipment = mapper.writeValueAsString(dto);

        mvc.perform(post("/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEquipment))
                .andExpect(status().isCreated());
    }

    // Teste l'interdiction de suppression pour un simple USER (403 FORBIDDEN)
    @Test
    @WithMockUser(roles = {"USER"})
    public void callDeleteEquipmentAsUser_shouldReturnCode403() throws Exception {
        mvc.perform(delete("/equipment/1"))
                .andExpect(status().isForbidden());
    }

    // Teste la suppression par un ADMIN (204 No Content)
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void callDeleteEquipmentAsAdmin_shouldReturnCode204() throws Exception {
        mvc.perform(delete("/equipment/4"))
                .andExpect(status().isNoContent());
    }

    // Teste la structure du JSON retourné sans exposer de données sensibles (JsonPath)
    @Test
    @WithMockUser(roles = {"USER"})
    public void callGetEquipment_shouldReturnValidFields() throws Exception {
        mvc.perform(get("/equipment/1"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    // Teste l'interdiction de création pour un simple USER (403 FORBIDDEN)
    @Test
    @WithMockUser(roles = {"USER"})
    public void callCreateEquipmentAsUser_shouldReturnCode403() throws Exception {
        EquipmentCreateDto dto = new EquipmentCreateDto("Écran Dell 27", 1, 1, 1);
        String jsonEquipment = mapper.writeValueAsString(dto);

        mvc.perform(post("/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEquipment))
                .andExpect(status().isForbidden());
    }

    // Teste la récupération des emprunts d'un équipement (200 OK)
    @Test
    @WithMockUser(roles = {"USER"})
    public void callGetLoansForEquipment_shouldReturnCode200() throws Exception {
        mvc.perform(get("/equipment/1/loans"))
                .andExpect(status().isOk());
    }

    // Teste l'interdiction de modification pour un USER (403 FORBIDDEN)
    @Test
    @WithMockUser(roles = {"USER"})
    public void callUpdateEquipmentAsUser_shouldReturnCode403() throws Exception {
        EquipmentCreateDto dto = new EquipmentCreateDto("Écran Modifié", 1, 1, 1);
        String jsonEquipment = mapper.writeValueAsString(dto);

        mvc.perform(put("/equipment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEquipment))
                .andExpect(status().isForbidden());
    }

    // Teste la modification par un ADMIN (200 OK)
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void callUpdateEquipmentAsAdmin_shouldReturnCode200() throws Exception {
        EquipmentCreateDto dto = new EquipmentCreateDto("Écran Dell Modifié", 1, 1, 1);
        String jsonEquipment = mapper.writeValueAsString(dto);

        mvc.perform(put("/equipment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEquipment))
                .andExpect(status().isOk());
    }
}
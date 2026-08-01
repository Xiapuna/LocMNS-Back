package com.mns.cda.locmnsback.unit;

import com.mns.cda.locmnsback.controller.EquipmentController;
import com.mns.cda.locmnsback.dto.EquipmentCreateDto;
import com.mns.cda.locmnsback.dto.EquipmentDto;
import com.mns.cda.locmnsback.dto.EquipmentUpdateDto;
import com.mns.cda.locmnsback.dto.LoanCalendarDto;
import com.mns.cda.locmnsback.mock.MockEquipmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class EquipmentControllerUnitTest {

    // Test du cas nominal : récupération d'un équipement existant
    @Test
    public void getEquipmentByExistingId_ShouldReturnCode200() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());
        ResponseEntity<EquipmentDto> response = equipmentController.get(1);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Projecteur Epson", response.getBody().name());
    }

    // Test du cas d'erreur : tentative de récupération d'un équipement inexistant
    @Test
    public void getEquipmentByNotExistingId_ShouldReturnCode404() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());

        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> equipmentController.get(2)
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    // Test de la liste globale
    @Test
    public void getAllEquipment_ShouldReturnCode200() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());
        ResponseEntity<List<EquipmentDto>> response = equipmentController.getAll();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    // Test des emprunts par équipement
    @Test
    public void getLoansForEquipment_ShouldReturnCode200() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());
        ResponseEntity<List<LoanCalendarDto>> response = equipmentController.getLoansForEquipment(1);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    // Test de la création d'équipement
    @Test
    public void createEquipment_ShouldReturnCode201() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());
        EquipmentCreateDto dto = new EquipmentCreateDto("Écran Dell", 1, 1, 1);

        ResponseEntity<EquipmentDto> response = equipmentController.create(dto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Écran Dell", response.getBody().name());
    }

    // Test de la suppression d'un équipement existant
    @Test
    public void deleteExistingEquipment_ShouldReturnCode204() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());
        ResponseEntity<Void> response = equipmentController.delete(1);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test de la suppression d'un équipement inexistant
    @Test
    public void deleteNotExistingEquipment_ShouldReturnCode404() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());

        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> equipmentController.delete(2)
        );
    }

    // Test de la mise à jour d'un équipement existant
    @Test
    public void updateExistingEquipment_ShouldReturnCode200() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());
        EquipmentUpdateDto dto = new EquipmentUpdateDto("Projecteur HD", 1, 1, 1);

        ResponseEntity<EquipmentDto> response = equipmentController.update(1, dto);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Projecteur HD", response.getBody().name());
    }

    // Test de la mise à jour d'un équipement inexistant
    @Test
    public void updateNotExistingEquipment_ShouldReturnCode404() {
        EquipmentController equipmentController = new EquipmentController(new MockEquipmentService());
        EquipmentUpdateDto dto = new EquipmentUpdateDto("Projecteur HD", 1, 1, 1);

        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> equipmentController.update(2, dto)
        );
    }
}
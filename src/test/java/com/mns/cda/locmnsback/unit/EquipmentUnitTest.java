package com.mns.cda.locmnsback.unit;

import com.mns.cda.locmnsback.TestUtils;
import com.mns.cda.locmnsback.model.Equipment;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EquipmentUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        // Initialisation du validateur pour les tests unitaires
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // Test du champ obligatoire / non vide (NotBlank) sur le nom
    @Test
    public void validEquipmentWithBlankName_shouldNotBeValid() {
        Equipment equipment = new Equipment();
        equipment.setName("");

        // Simulation de la validation avec TestUtils
        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "name",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    // 1. Test de la limite de taille (Length) sur le nom
    @Test
    public void validEquipmentWithTooLongName_shouldNotBeValid() {
        Equipment equipment = new Equipment();

        // On génère une chaîne de 501 caractères (limite max = 500)
        equipment.setName("a".repeat(501));
        equipment.setCondition("Bon état"); // On remplit la condition pour isoler l'erreur

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "name",
                "Length" // On vérifie l'annotation @Length
        );

        Assertions.assertTrue(constraintExist, "La contrainte Length sur name n'a pas fonctionné");
    }

    // 2. Test du NotBlank sur le champ condition
    @Test
    public void validEquipmentWithBlankCondition_shouldNotBeValid() {
        Equipment equipment = new Equipment();
        equipment.setName("Projecteur");
        equipment.setCondition(""); // Condition vide

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "condition",
                "NotBlank" // On vérifie l'annotation @NotBlank
        );

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur condition n'a pas fonctionné");
    }

    // 3. Test de la limite de taille (Length) sur la condition
    @Test
    public void validEquipmentWithTooLongCondition_shouldNotBeValid() {
        Equipment equipment = new Equipment();
        equipment.setName("Projecteur");

        // On génère une chaîne de 101 caractères (limite max = 100)
        equipment.setCondition("a".repeat(101));

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "condition",
                "Length"
        );

        Assertions.assertTrue(constraintExist, "La contrainte Length sur condition n'a pas fonctionné");
    }
}
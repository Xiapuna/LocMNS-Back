package com.mns.cda.locmnsback;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class TestUtils {

    public static boolean constraintViolationExist(
            Set<ConstraintViolation<Object>> constraintsViolations,
            String fieldName,
            String annotationName
    ) {
        return constraintsViolations.stream()
                .anyMatch(contrainte -> {
                    String champs = contrainte.getPropertyPath().toString();
                    String erreur = contrainte
                            .getConstraintDescriptor()
                            .getAnnotation()
                            .annotationType()
                            .getSimpleName();

                    return champs.equals(fieldName) && erreur.equals(annotationName);
                });
    }
}
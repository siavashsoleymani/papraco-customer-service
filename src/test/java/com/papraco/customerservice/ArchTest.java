package com.papraco.customerservice;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.papraco.customerservice");

        noClasses()
            .that()
            .resideInAnyPackage("com.papraco.customerservice.service..")
            .or()
            .resideInAnyPackage("com.papraco.customerservice.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.papraco.customerservice.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}

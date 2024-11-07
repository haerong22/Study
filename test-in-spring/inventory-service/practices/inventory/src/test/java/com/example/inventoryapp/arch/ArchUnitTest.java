package com.example.inventoryapp.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchUnitTest {

    private final JavaClasses targetClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.example.inventoryapp.inventory");

    private final Architectures.LayeredArchitecture baseRule = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Repository").definedBy("..repository..");

    @DisplayName("Controller 레이어는 Service 레이어에만 의존한다.")
    @Test
    void test1() {
        final ArchRule rule = baseRule
                .whereLayer("Controller").mayOnlyAccessLayers("Service")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer();

        rule.check(targetClasses);
    }

    @DisplayName("Service 레이어는 어떤 레이어에도 의존한다.")
    @Test
    void test2() {
        final ArchRule rule = baseRule
                .whereLayer("Service").mayNotAccessAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Repository");

        rule.check(targetClasses);
    }

    @DisplayName("Repository 레이어는 Service 레이어에만 의존한다.")
    @Test
    void test3() {
        final ArchRule rule = baseRule
                .whereLayer("Repository").mayOnlyAccessLayers("Service")
                .whereLayer("Repository").mayNotBeAccessedByAnyLayer();

        rule.check(targetClasses);
    }
}

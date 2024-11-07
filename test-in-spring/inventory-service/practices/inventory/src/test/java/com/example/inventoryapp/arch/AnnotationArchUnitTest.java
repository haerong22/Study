package com.example.inventoryapp.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
        packages = "com.example.inventoryapp.inventory",
        importOptions = ImportOption.Predefined.DoNotIncludeTests.class
)
public class AnnotationArchUnitTest {

    private final Architectures.LayeredArchitecture baseRule = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Repository").definedBy("..repository..");

    // Controller 레이어는 Service 레이어에만 의존한다.
    @ArchTest
    final ArchRule rule1 = baseRule
            .whereLayer("Controller").mayOnlyAccessLayers("Service")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer();

    // Service 레이어는 어떤 레이어에도 의존한다.
    final ArchRule rule2 = baseRule
            .whereLayer("Service").mayNotAccessAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Repository");

    // Repository 레이어는 Service 레이어에만 의존한다.
    final ArchRule rule3 = baseRule
            .whereLayer("Repository").mayOnlyAccessLayers("Service")
            .whereLayer("Repository").mayNotBeAccessedByAnyLayer();
}

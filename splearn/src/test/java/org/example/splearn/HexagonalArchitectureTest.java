package org.example.splearn;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "org.example.splearn", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {

    @ArchTest
    void hexagonalArchitecture(JavaClasses classes) {
        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("domain").definedBy("org.example.splearn.domain..")
                .layer("application").definedBy("org.example.splearn.application..")
                .layer("adapter").definedBy("org.example.splearn.adapter..")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter")
                .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
                .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
                .check(classes);
    }
}

package com.example.apptest.study;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

public class ArchTests {

    @Test
    void packageDependencyTests_4() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.apptest");

        SliceRule freeOfCycles = SlicesRuleDefinition.slices().matching("..apptest.(*)..")
                .should().beFreeOfCycles();

        freeOfCycles.check(classes);
    }

    @Test
    void packageDependencyTests_3() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.apptest");

        ClassesShouldConjunction studyPackageRule =
                ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..study..")
                .should().accessClassesThat().resideInAPackage("..study..");

        studyPackageRule.check(classes);
    }

    @Test
    void packageDependencyTests_2() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.apptest");

        ClassesShouldConjunction memberPackageRule = ArchRuleDefinition.noClasses().that().resideInAPackage("..domain..")
                .should().accessClassesThat().resideInAnyPackage("..member..");

        memberPackageRule.check(classes);
    }

    @Test
    void packageDependencyTests() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.apptest");

        ClassesShouldConjunction domainPackageRule = ArchRuleDefinition.classes().that().resideInAPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat().resideInAnyPackage("..study..", "..member..", "..domain..");

        domainPackageRule.check(classes);
    }
}

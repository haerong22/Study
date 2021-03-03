package com.example.apptest.study;

import com.example.apptest.App;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

// App.class 가 있는 패키지 선택
@AnalyzeClasses(packagesOf = App.class)
public class ArchJUnitTest {

    // 룰 정의 후 테스트
    @ArchTest
    SliceRule freeOfCycles = SlicesRuleDefinition.slices().matching("..apptest.(*)..")
            .should().beFreeOfCycles();

    @ArchTest
    ClassesShouldConjunction studyPackageRule =
            ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..study..")
                    .should().accessClassesThat().resideInAPackage("..study..");

    @ArchTest
    ClassesShouldConjunction memberPackageRule = ArchRuleDefinition.noClasses().that().resideInAPackage("..domain..")
            .should().accessClassesThat().resideInAnyPackage("..member..");

    @ArchTest
    ClassesShouldConjunction domainPackageRule = ArchRuleDefinition.classes().that().resideInAPackage("..domain..")
            .should().onlyBeAccessed().byClassesThat().resideInAnyPackage("..study..", "..member..", "..domain..");

}

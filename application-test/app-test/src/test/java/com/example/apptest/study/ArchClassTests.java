package com.example.apptest.study;

import com.example.apptest.App;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;

import javax.persistence.Entity;

@AnalyzeClasses(packagesOf = App.class)
public class ArchClassTests {

//    @ArchTest
//    ClassesShouldConjunction controllerClassRule =
//            ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Controller")
//                    .should().accessClassesThat().haveSimpleNameEndingWith("Service")
//                    .orShould().accessClassesThat().haveSimpleNameEndingWith("Repository");

//    @ArchTest
//    ClassesShouldConjunction repositoryClassRule =
//            ArchRuleDefinition.noClasses().that().haveSimpleNameEndingWith("Repository")
//                    .should().accessClassesThat().haveSimpleNameEndingWith("Service")
//                    .orShould().accessClassesThat().haveSimpleNameEndingWith("Controller");

    @ArchTest
    ClassesShouldConjunction studyClassesRule =
            ArchRuleDefinition.classes().that().haveSimpleNameStartingWith("Study")
                    .and().areNotEnums()
                    .and().areNotAnnotatedWith(Entity.class)
                    .should().resideInAnyPackage("..study..");
}

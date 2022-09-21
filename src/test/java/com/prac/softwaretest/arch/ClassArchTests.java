package com.prac.softwaretest.arch;

import com.prac.softwaretest.SoftwareTestApplication;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packagesOf = SoftwareTestApplication.class)
public class ClassArchTests {

    @ArchTest
    ArchRule controllerClassReferServiceRule
            = classes().that()
            .haveSimpleNameEndingWith("Controller")
            .should()
            .accessClassesThat()
            .haveSimpleNameEndingWith("Service");

    @ArchTest
    ArchRule controllerClassNotReferRepositoryRule
            = noClasses().that()
            .haveSimpleNameEndingWith("Controller")
            .should()
            .dependOnClassesThat()
            .haveSimpleNameEndingWith("Repository");

    @ArchTest
    ArchRule serviceClassReferRepositoryRule
            = classes().that()
            .haveSimpleNameEndingWith("Service")
            .should()
            .dependOnClassesThat()
            .haveSimpleNameEndingWith("Repository");

}

package com.prac.softwaretest.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class PackageArchTests {

    // 패키지 경로의 클래스들 가져옴
    private static final JavaClasses classes = new ClassFileImporter()
            .importPackages("com.prac.softwaretest");

    @Test
    @DisplayName("domain 패키지 클래스들은 domain, member, post, dto 에서만 참조 가능하다.")
    void domainPackageDependencyTest() {
        //import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition
        classes().that()
                .resideInAPackage("..domain..")  // domain키워드가 있는 패키지 안에 있는 클래스들은
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAnyPackage("..domain..", "..member..", "..post..", "..dto..")// 에서만 참조할 수 있다.
                .check(classes);
        // 테스트 패키지도 포함되니 주의할 것
    }

    @Test
    @DisplayName("member 패키지의 클래스들은 member, post 패키지의 클래스들에서만 참조 가능하다.")
    void memberPackageShouldBeReferencedBymemberAndpostPackages() {
        classes().that()
                .resideInAPackage("..member..")
                .should()
                .onlyBeAccessed()
                .byClassesThat()
                .resideInAnyPackage("..member..", "..post..")
                .check(classes);
    }

    @Test
    @DisplayName("post 패키지의 클래스들은 post 패키지 내부의 클래스들만 참조 가능하다")
    void postPackageShouldBeSelfReferenced() {
        noClasses().that()// 없어야한다.
                .resideOutsideOfPackage("..post..") // post 키워드 없는 패키지들의 클래스들은
                .should()
                .accessClassesThat()
                .resideInAPackage("..post..") // post 키워드 패키지의 클래스들을 참조하는 것이
                .check(classes);
    }

    @Test
    @DisplayName("애플리케이션 내부의 클래스들 간 순환참조가 없어야 한다.")
    void classesShouldBeFreeOfCycles() {
        SlicesRuleDefinition.slices()
                .matching("..softwaretest.(*)..")
                .should()
                .beFreeOfCycles()
                .check(classes);
    }
}

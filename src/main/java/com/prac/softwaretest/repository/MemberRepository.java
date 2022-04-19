package com.prac.softwaretest.repository;

import com.prac.softwaretest.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

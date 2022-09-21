package com.prac.softwaretest.post;

import com.prac.softwaretest.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByMemberIdAndId(Long memberId, Long id);
}

package com.prac.softwaretest.post;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.Post;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.member.MemberRepository;
import com.prac.softwaretest.post.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private Member member;
    private Post post;

    @BeforeAll
    void beforeAll() {
        member = memberRepository.save(SampleMember.of());
        post = postRepository.save(Post.builder().title("제목").content("내용").member(member).build());
        assertThat(member.getId()).isNotNull();
        assertThat(post.getId()).isNotNull();
    }

    @Test
    void findExistPostByMemberIdAndPostIdShouldPresent() {
        assertThat(postRepository.findByMemberIdAndId(member.getId(), post.getId()))
                .isPresent();
    }

    @Test
    void findNotExistPostByMemberIdAndPostIdShouldNotPresent() {
        assertThat(postRepository.findByMemberIdAndId(member.getId(), 9999L))
                .isNotPresent();
    }
}

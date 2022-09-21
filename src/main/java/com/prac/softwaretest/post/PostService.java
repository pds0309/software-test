package com.prac.softwaretest.post;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.Post;
import com.prac.softwaretest.dto.PostAllResponse;
import com.prac.softwaretest.dto.PostModificationRequest;
import com.prac.softwaretest.dto.PostModificationResponse;
import com.prac.softwaretest.exception.MemberNotFoundException;
import com.prac.softwaretest.exception.PostNotFoundException;
import com.prac.softwaretest.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostModificationResponse add(Long memberId, PostModificationRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .build());
        return new PostModificationResponse(post.getId());
    }

    public PostAllResponse getByPostId(Long memberId, Long postId) {
        Post post = postRepository.findByMemberIdAndId(memberId, postId)
                .orElseThrow(PostNotFoundException::new);
        return PostAllResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .memberId(memberId)
                .build();
    }
}
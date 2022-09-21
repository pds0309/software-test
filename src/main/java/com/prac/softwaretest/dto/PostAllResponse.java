package com.prac.softwaretest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAllResponse {
    private long postId;
    private long memberId;
    private String title;
    private String content;
}

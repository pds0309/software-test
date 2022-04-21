package com.prac.softwaretest.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberInfoResponse {
    private Long id;
    private String name;
}

package com.prac.softwaretest.dto;


import com.prac.softwaretest.domain.Member;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 200)
    private Integer age;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .age(age)
                .build();
    }
}

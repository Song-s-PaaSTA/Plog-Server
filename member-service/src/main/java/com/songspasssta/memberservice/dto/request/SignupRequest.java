package com.songspasssta.memberservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 100, message = "닉네임은 100자 이내로 입력해주세요.")
    private String nickname;
}

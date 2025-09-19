package sto.study_plaza.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignUpRequest {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;



}

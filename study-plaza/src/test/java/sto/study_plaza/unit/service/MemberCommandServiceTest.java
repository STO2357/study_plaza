package sto.study_plaza.unit.service;

import sto.study_plaza.testutil.TestEntityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import sto.study_plaza.dto.auth.LogInRequest;
import sto.study_plaza.dto.auth.SignUpRequest;
import sto.study_plaza.entity.Member;
import sto.study_plaza.repository.member.MemberRepository;
import sto.study_plaza.service.member.MemberCommandService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    MemberCommandService memberCommandService;

    @Test
    void signUpSuccess() throws IOException {
        // given
        SignUpRequest request = new SignUpRequest("user1", "홍길동", "pass123");
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass");

        // save 호출 시 ID 넣어서 반환
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            return TestEntityUtil.setRandomId(member); // ✅ 유틸 사용
        });

        // when
        UUID result = memberCommandService.signUp(request);

        // then
        assertNotNull(result);
        verify(passwordEncoder).encode("pass123");
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void loginSuccess() {
        // given
        UUID memberId = UUID.randomUUID();
        LogInRequest request = new LogInRequest("user1", "pass123");

        Member member = Member.builder()
                .userId("user1")
                .password("encodedPass")
                .name("홍길동")
                .build();
        TestEntityUtil.setId(member, memberId); // ✅ 유틸 사용

        when(memberRepository.findByUserId("user1")).thenReturn(Optional.of(member));
        when(passwordEncoder.matches("pass123", "encodedPass")).thenReturn(true);

        // when
        UUID result = memberCommandService.login(request);

        // then
        assertEquals(memberId, result);
    }

    @Test
    void loginUserNotExist() {
        // given
        LogInRequest request = new LogInRequest("user1", "pass123");
        when(memberRepository.findByUserId("user1")).thenReturn(Optional.empty());

        // when & then
        assertThrows(BadCredentialsException.class,
                () -> memberCommandService.login(request));
    }

    @Test
    void loginWrongPassword() {
        // given
        Member member = Member.builder()
                .userId("user1")
                .password("encodedPass")
                .name("홍길동")
                .build();
        TestEntityUtil.setRandomId(member); // ✅ 유틸 사용

        when(memberRepository.findByUserId("user1")).thenReturn(Optional.of(member));
        when(passwordEncoder.matches("pass123", "encodedPass")).thenReturn(false);

        LogInRequest request = new LogInRequest("user1", "pass123");

        // when & then
        assertThrows(BadCredentialsException.class,
                () -> memberCommandService.login(request));
    }

}

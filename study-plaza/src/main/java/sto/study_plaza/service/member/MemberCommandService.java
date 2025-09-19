package sto.study_plaza.service.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sto.study_plaza.dto.auth.LogInRequest;
import sto.study_plaza.dto.auth.SignUpRequest;
import sto.study_plaza.entity.Member;
import sto.study_plaza.repository.member.MemberRepository;
import sto.study_plaza.util.JwtUtil;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UUID signUp(SignUpRequest signUpRequest) throws IOException {

        String userId = signUpRequest.getUserId();
        String rawPassword = signUpRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String name = signUpRequest.getName();

        Member member = Member.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(name)
                .build();

        memberRepository.save(member);

        return member.getId();
    }

    public String login(LogInRequest logInRequest) {

        String userId = logInRequest.getUserId();
        String password = logInRequest.getPassword();
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new BadCredentialsException("User Not Found") {
                });
        if (member == null) {
            throw new BadCredentialsException("User not found") {
            };
        }
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("Invalid credentials") {
            };
        }

        return jwtUtil.generateToken(userId, member.getId());
    }


}

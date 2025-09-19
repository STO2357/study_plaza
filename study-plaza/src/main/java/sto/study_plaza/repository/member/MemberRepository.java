package sto.study_plaza.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import sto.study_plaza.entity.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID>, MemberRepositoryCustom {

    Optional<Member> findByUserId(String userId);

}

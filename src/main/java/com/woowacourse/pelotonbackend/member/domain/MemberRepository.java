package com.woowacourse.pelotonbackend.member.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    @Override
    List<Member> findAll();

    @Override
    List<Member> findAllById(Iterable<Long> ids);

    Optional<Member> findByKakaoId(Long kakaoId);

    boolean existsByKakaoId(Long kakaoId);
}

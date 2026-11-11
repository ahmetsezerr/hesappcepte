package com.hesappcepte.areamember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaMemberRepository extends JpaRepository<AreaMember, Long> {
    List<AreaMember> findByUser_IdAndStatus(Long userId, AreaMemberStatus areaMemberStatus);
    Optional<AreaMember> findByUser_IdAndFinanceArea_IdAndStatus(Long userId, Long financeAreaId, AreaMemberStatus status);
}
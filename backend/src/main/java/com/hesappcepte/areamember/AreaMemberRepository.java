package com.hesappcepte.areamember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaMemberRepository
        extends JpaRepository<AreaMember, Long> {


    List<AreaMember> findByFinanceArea_IdAndStatus(Long financeAreaId, AreaMemberStatus status);
}
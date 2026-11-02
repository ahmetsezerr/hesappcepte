package com.hesappcepte.account;

import com.hesappcepte.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByOwnerId(Long ownerId);
    Optional<Account> findByIdAndOwner_Id(Long accountId, Long ownerId);


}

package com.hesappcepte.account;

import com.hesappcepte.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {

}

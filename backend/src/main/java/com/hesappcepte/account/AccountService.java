package com.hesappcepte.account;

import com.hesappcepte.account.dto.AccountCreateRequest;
import com.hesappcepte.account.dto.AccountResponse;
import com.hesappcepte.user.User;
import com.hesappcepte.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(
            AccountRepository accountRepository,
            UserRepository userRepository
    ) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }



    @Transactional
    public AccountResponse createAccount(
            AccountCreateRequest request,
            Long userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow();

        Account account = new Account(
                user,
                request.name().trim(),
                request.accountType(),
                request.currency().trim().toUpperCase(Locale.ROOT),
                request.initialBalance()
        );

        Account savedAccount = accountRepository.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getName(),
                savedAccount.getAccountType(),
                savedAccount.getCurrency(),
                savedAccount.getInitialBalance(),
                savedAccount.getCurrentBalance(),
                savedAccount.getStatus()
        );
    }


}

package com.hesappcepte.account;

import com.hesappcepte.account.dto.AccountCreateRequest;
import com.hesappcepte.account.dto.AccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountCreateRequest request, Authentication authentication){

        Long userId = (Long) authentication.getPrincipal();

        AccountResponse response = accountService.createAccount(request,userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }
}

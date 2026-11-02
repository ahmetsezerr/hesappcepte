package com.hesappcepte.account;

import com.hesappcepte.account.dto.AccountCreateRequest;
import com.hesappcepte.account.dto.AccountResponse;
import com.hesappcepte.account.dto.AccountUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(Authentication authentication){

        Long ownerId = (Long) authentication.getPrincipal();

        List<AccountResponse> responses = accountService.getAccounts(ownerId);

        return ResponseEntity.ok(responses);

    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long accountId, Authentication authentication){

        Long userId = (Long) authentication.getPrincipal();

        AccountResponse response = accountService.getAccountById(accountId,userId);

        return ResponseEntity.ok(response);

    }
    @PatchMapping("/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable  Long accountId,
             @RequestBody @Valid AccountUpdateRequest request,
            Authentication authentication){

        Long ownerId = (Long) authentication.getPrincipal();

        AccountResponse response = accountService.updateAccount(accountId,ownerId,request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{accountId}/archive")
    public ResponseEntity<AccountResponse> archiveAccount(
            @PathVariable Long accountId,
            Authentication authentication) {

        Long ownerId = (Long) authentication.getPrincipal();

        AccountResponse response =
                accountService.archiveAccount(accountId, ownerId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{accountId}/activate")
    public ResponseEntity<AccountResponse> activateAccount(
            @PathVariable Long accountId,
            Authentication authentication){
        Long ownerId = (Long) authentication.getPrincipal();

        AccountResponse response =
                accountService.activateAccount(accountId, ownerId);

        return ResponseEntity.ok(response);
    }

}




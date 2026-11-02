package com.hesappcepte.account;

import com.hesappcepte.account.dto.AccountCreateRequest;
import com.hesappcepte.account.dto.AccountResponse;
import com.hesappcepte.account.dto.AccountUpdateRequest;
import com.hesappcepte.common.exception.BusinessException;
import com.hesappcepte.common.exception.ErrorCode;
import com.hesappcepte.user.User;
import com.hesappcepte.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<AccountResponse> getAccounts(Long ownerId){

        List<Account> accounts = accountRepository.findByOwnerId(ownerId);

        List<AccountResponse> responses = new ArrayList<>();

        for (Account account : accounts) {

            AccountResponse response = new AccountResponse(
                    account.getId(),
                    account.getName(),
                    account.getAccountType(),
                    account.getCurrency(),
                    account.getInitialBalance(),
                    account.getCurrentBalance(),
                    account.getStatus()
            );

            responses.add(response);
        }

        return responses;

      }

      public AccountResponse getAccountById(Long accountId, Long ownerId){

        Account account = accountRepository.findByIdAndOwner_Id(accountId,ownerId)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND)
                );
        AccountResponse response = new AccountResponse(
                account.getId(),
                account.getName(),
                account.getAccountType(),
                account.getCurrency(),
                account.getInitialBalance(),
                account.getCurrentBalance(),
                account.getStatus()
            );
        return response;
      }


      @Transactional
      public AccountResponse updateAccount(Long accountId,
                                           Long ownerId,
                                           AccountUpdateRequest request){

          Account account = accountRepository.findByIdAndOwner_Id(accountId,ownerId)
                  .orElseThrow(() ->
                          new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND)
                  );


          account.setName(request.name().trim());

          AccountResponse accountResponse = new AccountResponse(
                  account.getId(),
                  account.getName(),
                  account.getAccountType(),
                  account.getCurrency(),
                  account.getInitialBalance(),
                  account.getCurrentBalance(),
                  account.getStatus()
          );
          return accountResponse;


      }
    @Transactional
      public AccountResponse archiveAccount(Long accountId,Long ownerId) {

          Account account = accountRepository.findByIdAndOwner_Id(accountId, ownerId)
                  .orElseThrow(() ->
                          new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND)
                  );
          account.archive();

          AccountResponse response = new AccountResponse(
                  account.getId(),
                  account.getName(),
                  account.getAccountType(),
                  account.getCurrency(),
                  account.getInitialBalance(),
                  account.getCurrentBalance(),
                  account.getStatus()
          );
        return response;

      }

@Transactional
      public AccountResponse activateAccount(Long accountId, Long ownerId){

        Account account = accountRepository.findByIdAndOwner_Id(accountId,ownerId)
                .orElseThrow(()->
                        new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));


        account.activate();

        AccountResponse response = new AccountResponse(
                account.getId(),
                account.getName(),
                account.getAccountType(),
                account.getCurrency(),
                account.getInitialBalance(),
                account.getCurrentBalance(),
                account.getStatus()
        );
        return response;
      }


}

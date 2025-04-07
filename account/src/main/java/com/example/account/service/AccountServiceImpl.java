package com.example.account.service;


import com.example.account.controller.request.AccountRequest;
import com.example.account.controller.response.AccountResponse;
import com.example.account.entity.Account;
import com.example.account.entity.AccountProfile;
import com.example.account.repository.AccountProfileRepository;
import com.example.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountProfileRepository profileRepository;

    @Override
    public AccountResponse createIfNotExists(AccountRequest request) {
        return accountRepository.findByProviderAndProviderId(request.provider(), request.providerId())
                .map(account -> new AccountResponse(account.getId(), account.getProfile().getEmail(), account.getProfile().getName()))
                .orElseGet(() -> {
                    AccountProfile profile = AccountProfile.builder()
                            .email(request.email())
                            .name(request.name())
                            .pictureUrl(request.pictureUrl())
                            .build();
                    profileRepository.save(profile);

                    Account account = Account.builder()
                            .provider(request.provider())
                            .providerId(request.providerId())
                            .profile(profile)
                            .build();
                    accountRepository.save(account);

                    return new AccountResponse(account.getId(), profile.getEmail(), profile.getName());
                });
    }
}

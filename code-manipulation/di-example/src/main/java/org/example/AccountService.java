package org.example;

import org.example.di.Inject;

public class AccountService {

    @Inject
    AccountRepository accountRepository;

    public void join() {
        System.out.println("Service.join()");
        accountRepository.save();
    }
}

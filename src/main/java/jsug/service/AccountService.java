package jsug.service;

import jsug.model.Account;
import jsug.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public boolean isUnusedEmail(String email) {
        return accountRepository.countByEmail(email) == 0;
    }

    public Account register(Account account, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        account.setPassword(encodedPassword);
        return accountRepository.create(account);
    }
}

package auctionbe.service;

import auctionbe.models.Account;
import auctionbe.models.Role;
import auctionbe.repository.AccountRepository;
import auctionbe.utils.EncryptPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    /* Set password by token */
    public void setPasswordByToken(String token, String email) {
        Account account = accountRepository.findAccountByEmail(email);

        if (account != null) {
            account.setToken(token);
            accountRepository.save(account);
        } else {
            throw new UsernameNotFoundException("Error: We cannot find your account registered:" + email);
        }
    }

    /* Find by id */
    public Account findAccountById(String id) throws Exception {
        Optional<Account> account = accountRepository.findById(id);
        if(!account.isPresent()) {
            throw new Exception("User doesn't existing");
        }

        return account.get();
    }

    /* Get user by token */
    public Account findAccountByToken(String token) {
        return accountRepository.findAccountByToken(token);
    }

    /* Update password*/
    public Account updatePassword(Account account, String newPassword) {
        account.setPassword(EncryptPasswordUtils.EncryptPasswordUtils(newPassword));

        /* Remove token */
        account.setToken(null);

        return accountRepository.save(account);
    }

    /* Get the password of the previously used user by resetting the pwd token*/
    public boolean checkPasswordUsed(Account account, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(newPassword, account.getPassword())) {
//            throw new IllegalArgumentException("You used this password recently. Please choose a different one.");
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByEmail(username);

        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        /* Return 3 properties is roles , username and password*/
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Role> roles = account.getRoles();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                account.getEmail(), account.getPassword(), grantedAuthorities
        );
    }
}

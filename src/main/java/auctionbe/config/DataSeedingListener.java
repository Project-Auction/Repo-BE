package auctionbe.config;

import auctionbe.models.Account;
import auctionbe.models.Rank;
import auctionbe.models.Role;
import auctionbe.models.User;
import auctionbe.repository.AccountRepository;
import auctionbe.repository.RankRepository;
import auctionbe.repository.RoleRepository;
import auctionbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Seeding data test for table users and roles
 */
@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private UserRepository userRepository;

    public static String EncryptPasswordUtils(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (roleRepository.findByNameRole("ROLE_MANAGER")==null){
            roleRepository.save(new Role("ROLE_MANAGER"));
        }

        if (roleRepository.findByNameRole("ROLE_MEMBER")==null){
            roleRepository.save(new Role("ROLE_MEMBER"));
        }
        if (roleRepository.findByNameRole("ROLE_BLOCK")==null){
            roleRepository.save(new Role("ROLE_BLOCK"));
        }

        //Adding rank
        if (rankRepository.findByName("BROZER")==null){
            rankRepository.save(new Rank("BROZER"));
        }

        if (rankRepository.findByName("SILVER")==null){
            rankRepository.save(new Rank("SILVER"));
        }

        if (rankRepository.findByName("RANK_GOLD")==null){
            rankRepository.save(new Rank("RANK_GOLD"));
        }

        if (rankRepository.findByName("PLATINUM")==null){
            rankRepository.save(new Rank("PLATINUM"));
        }

        if (rankRepository.findByName("DIAMOND")==null){
            rankRepository.save(new Rank("DIAMOND"));
        }

        // Add manager
        if (accountRepository.findAccountByEmail("manager@aution.com") == null){
            Account manager = new Account();
            User userSave = new User();
            manager.setEmail("manager@aution.com");
            manager.setPassword(EncryptPasswordUtils("123123"));
            manager.setIsBlocked(false);
            manager.setLastLogin(String.valueOf(LocalDate.now()));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByNameRole("ROLE_MANAGER"));
            roles.add(roleRepository.findByNameRole("ROLE_MEMBER"));
            manager.setRoles(roles);

            /* Save user */
            userSave.setName("Admin");
            userSave.setAccount(manager);

            /* Set rank */
            userSave.setRank(rankRepository.findByName("BROZER"));

            userRepository.save(userSave);
            accountRepository.save(manager);
        }

        //Add users
        if (accountRepository.findAccountByEmail("member@aution.com") == null){
            Account member = new Account();
            User userSave = new User();
            member.setEmail("member@aution.com");
            member.setPassword(EncryptPasswordUtils("123123"));
            member.setIsBlocked(false);
            member.setLastLogin(String.valueOf(LocalDate.now()));
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByNameRole("ROLE_MEMBER"));
            member.setRoles(roles);

            /* Save user */
            userSave.setName("Member");
            userSave.setAccount(member);
            
            /* Set rank */
            userSave.setRank(rankRepository.findByName("BROZER"));

            userRepository.save(userSave);
            accountRepository.save(member);
        }
    }
}

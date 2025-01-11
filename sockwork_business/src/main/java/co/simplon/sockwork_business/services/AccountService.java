package co.simplon.sockwork_business.services;

import co.simplon.sockwork_business.config.JwtProvider;
import co.simplon.sockwork_business.dtos.AccountCreate;
import co.simplon.sockwork_business.dtos.AuthInfo;
import co.simplon.sockwork_business.entities.AccountEntity;
import co.simplon.sockwork_business.entities.RoleEntity;
import co.simplon.sockwork_business.jpaRepositories.AccountJpaRepo;
import co.simplon.sockwork_business.config.SecurityConfig;

import co.simplon.sockwork_business.jpaRepositories.RoleJpaRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AccountService {
    AccountJpaRepo accountJpaRepo;
    RoleJpaRepo roleJpaRepo;
    private final JwtProvider jwtProvider;

    public AccountService(AccountJpaRepo accountJpaRepo, JwtProvider jwtProvider, RoleJpaRepo roleJpaRepo) {
        this.accountJpaRepo = accountJpaRepo;
        this.jwtProvider = jwtProvider;
        this.roleJpaRepo = roleJpaRepo;
    }

    @Transactional
    public void create(AccountCreate inputs) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(inputs.username());
        accountEntity.setPassword(SecurityConfig.hashEncoder(inputs.password()));
        RoleEntity roles = roleJpaRepo.getDefaultManagerRole();
        accountEntity.addRoles(roles);
        System.out.println(roles);
        accountJpaRepo.save(accountEntity);
        System.out.println(inputs);
        System.out.println(accountEntity.getRoles());
    }

    public AuthInfo getAccountById(AccountCreate inputs) {
        AccountEntity accountEntity = accountJpaRepo.findAllByUsernameIgnoreCase(inputs.username())
                .orElseThrow(() -> new BadCredentialsException(inputs.username())); // c'est une référence vers. Le code n'est pas executé de suite. Programmation fonctionnel

        System.out.println("this is account entity : " + accountEntity);
        //Verify password
        boolean isPassWordMatch = SecurityConfig.verifyPassword(inputs.password(), accountEntity.getPassword());
        if(!isPassWordMatch) { //approche impératif
            throw new BadCredentialsException(inputs.username());
        }
        System.out.println(accountEntity);

        List<String> tolistRole = accountEntity.getRoles().stream().map(name-> name.getCode()).toList();
        Set<String> roles = accountEntity.getRoles().stream().map(RoleEntity::getCode).collect(Collectors.toSet());
        System.out.println(roles);

        String jwt = jwtProvider.create(inputs.username(), roles);

        AuthInfo authInfo = new AuthInfo(jwt,roles);
        //?? Controller advice + lever l'erreur badCredential
        //temporary return
        System.out.println(authInfo);
            return authInfo;

    }

    public AccountEntity getEntity(Long id) {
        Optional<AccountEntity> accountEntity = accountJpaRepo.findById(id);
        return accountEntity.orElseThrow(()-> new IllegalArgumentException("User with this id do not exists : " + id));
    }
}

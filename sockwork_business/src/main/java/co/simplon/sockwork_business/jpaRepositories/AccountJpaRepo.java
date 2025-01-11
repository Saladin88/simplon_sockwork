package co.simplon.sockwork_business.jpaRepositories;

import co.simplon.sockwork_business.entities.AccountEntity;
import co.simplon.sockwork_business.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AccountJpaRepo extends JpaRepository<AccountEntity,Long> {
    Optional<AccountEntity> findAllByUsernameIgnoreCase(String username);

}

package co.simplon.sockwork_business.jpaRepositories;

import co.simplon.sockwork_business.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleJpaRepo extends JpaRepository<RoleEntity, Long> {

    //for params = :params + inside methode namemethode(@param("params") type name)
    @Query(value="select * from t_roles where t_roles.default_code = true", nativeQuery = true)
    RoleEntity getDefaultManagerRole();
}

package co.simplon.sockwork_business.entities;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="t_accounts")
public class AccountEntity extends AbstractEntity {

    @Column(name="username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="t_accounts_roles",
            joinColumns = @JoinColumn(name="id_accounts"),
            inverseJoinColumns = @JoinColumn(name="id_roles")
    )
    Set<RoleEntity> roles = new HashSet<>();

    public AccountEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRoles(RoleEntity role){
        Objects.requireNonNull(role, "Role cannot be null");
        roles.add(role);
    }
    public Set<RoleEntity> getRoles() {
        return Collections.unmodifiableSet(roles);
    }


    @Override
    public String toString() {
        return "AccountEntity{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]" + '\'' +
                ", roles= LAZY_LOADED'}'";
    }
}

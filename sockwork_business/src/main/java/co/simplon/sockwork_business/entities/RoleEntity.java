package co.simplon.sockwork_business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="t_roles")
public class RoleEntity extends AbstractEntity {


    @Column(name="code")
    String code;

    @Column(name="description")
    String description;

    @Column(name="default_code")
    Boolean defaultCode;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    public Boolean getDefaultCode() {
        return defaultCode;
    }


    @Override
    public String toString() {
        return "RoleEntity{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", defaultCode=" + defaultCode +
                '}';
    }
}

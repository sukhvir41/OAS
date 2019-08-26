/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author sukhvir
 */

@Entity(name = "Admin")
@Table(name = "admin")
public class Admin implements Serializable {

    @Id
    @Getter
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "user_admin_foreign_key"))
    @Getter
    @NotNull
    private User user;


    @Column(name = "type", nullable = false, updatable = false)
    @NotNull
    private String type;

    public Admin() {
    }

    public Admin(String username, String password, String email, AdminType type) {
        this.user = User.createdActiveUser(username, password, email, -1, UserType.Admin);
        this.type = type.toString();
    }

    public AdminType getType() {
        return AdminType.valueOf(type);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id != null && id.equals(admin.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

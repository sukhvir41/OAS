/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import utility.Utils;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author sukhvir
 */

@NamedEntityGraph(name = "userAdmin",
        attributeNodes = @NamedAttributeNode("user"))
@Entity(name = "Admin")
@Table(name = "admin")
public class Admin implements Serializable {

    @Id
    @Getter
    private UUID id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId()
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "user_admin_foreign_key"))
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @Getter
    private User user;

    @Column(name = "type", nullable = false)
    private String type;

    public Admin() {
    }

    public Admin(String username, String password, String email, AdminType type) {
        this.user = new User(username, password, email, -1, UserType.Admin);
        this.type = type.toString();
    }

    public AdminType getType() {
        return AdminType.valueOf(type);
    }


    /**
     * gets the admin with eagerly fetched user.
     *
     * @param id      id of the admin
     * @param session hibernate session
     * @return
     */
    public static Admin getUserAdmin(UUID id, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Admin> query = builder.createQuery(Admin.class);
        Root<Admin> admin = query.from(Admin.class);
        query.where(
                builder.equal(admin.get(Admin_.id), id)
        );
        return session.createQuery(query)
                .setHint(Utils.LOAD_ENTITY_HINT, session.getEntityGraph("userAdmin"))
                .setMaxResults(1)
                .getSingleResult();
    }

    public static Admin getUserAdminReadOnly(UUID id, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Admin> query = builder.createQuery(Admin.class);
        Root<Admin> admin = query.from(Admin.class);
        query.where(
                builder.equal(admin.get(Admin_.id), id)
        );
        return session.createQuery(query)
                .setHint(Utils.LOAD_ENTITY_HINT, session.getEntityGraph("userAdmin"))
                .setMaxResults(1)
                .setReadOnly(true)
                .getSingleResult();
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

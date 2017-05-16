/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sukhvir
 */
@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {

    @Column(name = "type")
    @Convert(converter = AdminTypeConverter.class)
    @Getter
    @Setter
    private AdminType type;

    public Admin() {
    }

    public Admin(String username, String password, String email, AdminType type) {
        super(username, password, email, 0l);
        this.type = type;
    }

    @Override
    public User accept(Visitor visitor) {
        return visitor.visit(this);
    }

}

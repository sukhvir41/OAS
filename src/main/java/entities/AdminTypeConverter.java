/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.AttributeConverter;

/**
 *
 * @author sukhvir
 */

public class AdminTypeConverter implements AttributeConverter<AdminType, String> {

    @Override
    public String convertToDatabaseColumn(AdminType attribute) {
        return attribute.toString();
    }

    @Override
    public AdminType convertToEntityAttribute(String string) {
        if (AdminType.Main.toString().equals(string)) {
            return AdminType.Main;
        } else {
            return AdminType.Sub;
        }
    }

}

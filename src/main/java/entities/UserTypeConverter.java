package entities;

import javax.persistence.AttributeConverter;

public class UserTypeConverter implements AttributeConverter<UserType, String> {

	@Override
	public String convertToDatabaseColumn(UserType attribute) {
		return attribute.toString();
	}

	@Override
	public UserType convertToEntityAttribute(String string) {

		return UserType.valueOf( string.trim() );
	}

}

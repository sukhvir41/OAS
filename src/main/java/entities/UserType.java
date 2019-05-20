/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sukhvir
 */
public enum UserType {

	Admin( "Admin", "admin", Admin.class ,"entities.Admin"),
	Student( "Student", "student", Student.class,"entities.Student" ),
	Teacher( "Teacher", "teacher", Teacher.class , "entities.Teacher");

	@Getter
	final private String type;

	private final AtomicInteger count;

	@Getter
	final private String homeLink;

	@Getter
	final private Class userClass;

	@Getter
	final private String entityName;

	UserType(String type, String theLink, Class userClass, String entityName) {
		this.type = type;
		this.homeLink = theLink;
		this.count = new AtomicInteger( 0 );
		this.userClass = userClass;
		this.entityName = entityName;
	}

	@Override
	public String toString() {
		return type;
	}

	public void incrementCount() {
		count.incrementAndGet();
	}

	public int getCount() {
		return count.get();
	}

	public void decrementCount() {
		if ( count.get() != 0 ) {
			count.decrementAndGet();
		}
	}

}

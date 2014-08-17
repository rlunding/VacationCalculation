package org.lunding;

import java.io.Serializable;

/**
 * Class made to hold information about a person.
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class Person implements Serializable{
	
	private String name;
	private String mail;
	
	public Person(String name, String mail) {
		super();
		this.name = name;
		this.mail = mail;
	}
	
	public String getName() {
		return name;
	}
	
	
	public String getMail() {
		return mail;
	}
	
	@Override
	public String toString() {
		return "name: " + name;
	}

	/**
	 * Two persons are equal if their name and email are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(!(obj instanceof Currency)){
			return false;
		}
		Person p = (Person) obj;
		return p.name.equals(this.name) && p.mail.equals(this.mail);
	}
}

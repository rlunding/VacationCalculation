package org.lunding;

import java.io.Serializable;

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

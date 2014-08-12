package org.lunding;

public class Person {
	
	private String name;
	private String password;
	private String mail;
	
	public Person(String name, String password, String mail) {
		super();
		this.name = name;
		this.password = password;
		this.mail = mail;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getMail() {
		return mail;
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
		return p.name.equals(this.name) && p.password.equals(this.password) && p.mail.equals(this.mail);
	}
}

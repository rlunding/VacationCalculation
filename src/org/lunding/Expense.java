package org.lunding;

import java.math.BigDecimal;

public class Expense {
	
	private String title;
	private Person person;
	private BigDecimal amount;
	private Currency currency;
	
	public Expense(String title, Person person, BigDecimal amount, Currency currency) {
		super();
		this.title = title;
		this.person = person;
		this.amount = amount;
		this.currency = currency;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public Currency getCurrency() {
		return currency;
	}
}

package org.lunding;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Expense class. Contains all information about an expense.<br>
 * This will be: 
 * <ul>
 * 	<li>Title/name for expense</li>
 * 	<li>What person had the expense</li>
 * 	<li>The amount</li>
 *  <li>What currency</li>
 * </ul>
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class Expense implements Serializable{
	
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

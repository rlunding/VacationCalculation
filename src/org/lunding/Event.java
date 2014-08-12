package org.lunding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Event class. Contains all information about an event.<br>
 * This will be: 
 * <ul>
 * 	<li>Name of the event</li>
 * 	<li>What currency the end result should be given in</li>
 * 	<li>Persons attending the event</li>
 *  <li>All the expenses for this event</li>
 * </ul>
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class Event {
	
	private String name;
	private Currency currency;
	private ArrayList<Person> persons;
	private ArrayList<Expense> expenses;

	/**
	 * To create a new event the name of the event and what currency 
	 * the end result should be given in must be provided. <br>
	 * Persons and expenses can be added later on.
	 * @param name
	 * @param currency
	 */
	public Event(String name, Currency currency) {
		super();
		this.name = name;
		this.currency = currency;
		this.persons = new ArrayList<Person>();
		this.expenses = new ArrayList<Expense>();
	}

	public String getName() {
		return name;
	}

	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency c){
		this.currency = c;
	}

	public ArrayList<Person> getPersons() {
		return persons;
	}
	
	public boolean addPerson(Person p){
		return this.persons.add(p);
	}

	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
	
	public boolean addExpense(Expense e){
		return this.expenses.add(e);
	}

	/**
	 * This method will calculate:
	 * <ul>
	 * 	<li>Total expenses hold by each person</li>
	 * 	<li>Total expenses</li>
	 * 	<li>Total expenses pr. person</li>
	 * 	<li>Total expenses hold by each person (with price pr. person subtracted)</li>
	 * 	<li>Who need some money</li>
	 * 	<li>Who will give some money</li>
	 * 	<li>Who is going to pay who</li>
	 * </ul>
	 *  
	 */
	public void calculateWhoPayWho(){
		ArrayList<WhoPay> paylist = new ArrayList<WhoPay>();
		HashMap<Person, BigDecimal> expense = new HashMap<Person, BigDecimal>();
		ArrayList<Person> giveMoney = new ArrayList<Person>();
		ArrayList<Person> getMoney = new ArrayList<Person>();
		BigDecimal total = new BigDecimal(0);
		BigDecimal prPerson = new BigDecimal(0);
		
		System.out.println("\nWho had expenses, and how much (in " + currency.getCode() +"):");
		for(Person p : persons){
			expense.put(p, new BigDecimal(0));
			for(Expense e : expenses){
				if(e.getPerson().equals(p)){
					BigDecimal amount = getAmountInEventCurrency(e);
					expense.put(p, expense.get(p).add(amount));
					total = total.add(amount);
				}
			}
		}
		for(Person p : persons){
			System.out.println(p.getName() + " : " + expense.get(p));
		}
		prPerson = total.divide(new BigDecimal(persons.size()), 2, RoundingMode.HALF_UP);
		System.out.println("Total expenses: " + total.doubleValue() + " " + currency.getCode());
		System.out.println("Expenses pr. person: " + prPerson.doubleValue() + " " + currency.getCode());
		
		System.out.println("\nWho had expenses when the price pr. person is subtracted (in " + currency.getCode() +"):");
		for(Person p : persons){
			BigDecimal amount = expense.get(p).subtract(prPerson);
			expense.put(p, amount);
			if(amount.compareTo(BigDecimal.ZERO) > 0){
				getMoney.add(p);
			} else if(amount.compareTo(BigDecimal.ZERO) < 0){
				giveMoney.add(p);
			}
		}
		for(Person p : persons){
			System.out.println(p.getName() + " : " + expense.get(p));
		}
		
		System.out.println("\nThese need some money");
		for(Person p : getMoney){
			System.out.println(p.getName());
		}
		System.out.println("These will give some money");
		for(Person p : giveMoney){
			System.out.println(p.getName());
		}
		
		System.out.println("\nWho is going to pay who:");
		while(!getMoney.isEmpty() && !giveMoney.isEmpty()){
			Person get = getMoney.get(0);
			Person give = giveMoney.get(0);
			BigDecimal amountGet = expense.get(get);
			BigDecimal amountGive = expense.get(give).abs();
			BigDecimal pay = new BigDecimal(0);
			if(amountGet.compareTo(amountGive) > 0){
				pay = amountGive;
				giveMoney.remove(give);
				expense.remove(give);
				expense.put(get, expense.get(get).subtract(pay));
			} else if(amountGet.compareTo(amountGive) < 0){
				pay = amountGet;
				getMoney.remove(get);
				expense.remove(get);
				expense.put(give, expense.get(give).add(pay));
			} else {
				pay = amountGive;
				giveMoney.remove(give);
				getMoney.remove(get);
				expense.remove(get);
				expense.remove(give);
			}
			paylist.add(new WhoPay(give, get, pay, this.currency));
		}
		
		for(WhoPay wp : paylist){
			System.out.println(wp);
		}
	}
	
	/**
	 * This method calculate the amount for an expense in this events currency.<br>
	 * The method will return the amount in the events currency as a BigDecimal
	 * @param e
	 * @return BigDecimal with amount in event currency
	 */
	private BigDecimal getAmountInEventCurrency(Expense e){
		BigDecimal amount = e.getAmount();
		Currency currency = e.getCurrency();
		if(this.currency.equals(currency)){
			return amount;
		} else {
			return (amount.multiply(currency.getRate())).divide(this.currency.getRate(), 2, RoundingMode.HALF_UP);
		}
	}
}

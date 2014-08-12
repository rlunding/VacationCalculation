package org.lunding;

import java.math.BigDecimal;

public class VacationCalculation {
	
	public VacationCalculation(){
		//new LoginFrame();
		ExchangeRates rate = new ExchangeRates();
		//rate.printXML();
		Event e = new Event("O-ringen", rate.getCurrency("DKK"));
		Person rasmus = new Person("Rasmus", "123", "dr@dr.dk");
		Person lars = new Person("Lars", "123", "dr@dr.dk");
		Person edsen = new Person("Edsen", "123", "dr@dr.dk");
		Person laage = new Person("Laage", "123", "dr@dr.dk");
		Person mikkel = new Person("Mikkel", "123", "dr@dr.dk");
		Person eskil = new Person("Eskil", "123", "dr@dr.dk");
		e.addPerson(rasmus);
		e.addPerson(lars);
		e.addPerson(edsen);
		e.addPerson(laage);
		e.addPerson(mikkel);
		e.addPerson(eskil);
		
		e.addExpense(new Expense("Mad", edsen, new BigDecimal("413.41"), rate.getCurrency("SEK")));
		e.addExpense(new Expense("Vodka", eskil, new BigDecimal(2*57), rate.getCurrency("DKK")));
		e.addExpense(new Expense("Alkohol", eskil, new BigDecimal("247"), rate.getCurrency("DKK")));
		e.addExpense(new Expense("Pavilion", eskil, new BigDecimal("108"), rate.getCurrency("DKK")));
		e.addExpense(new Expense("Mad", rasmus, new BigDecimal("450"), rate.getCurrency("SEK")));
		e.addExpense(new Expense("Mad", laage, new BigDecimal("420"), rate.getCurrency("SEK")));
		e.addExpense(new Expense("Mad", mikkel, new BigDecimal("533.30"), rate.getCurrency("SEK")));
		e.addExpense(new Expense("Pizza", mikkel, new BigDecimal("355"), rate.getCurrency("SEK")));
		e.addExpense(new Expense("Grill", mikkel, new BigDecimal("117.5"), rate.getCurrency("SEK")));
		e.addExpense(new Expense("ukendt", mikkel, new BigDecimal("147"), rate.getCurrency("SEK")));
		e.addExpense(new Expense("Telt", lars, new BigDecimal("1300"), rate.getCurrency("SEK")));
		//e.calculateWhoPayWho();
		Serializer.serializeEvent(e);
		Event event = Serializer.deserialzeEvent();
		event.calculateWhoPayWho();
	}

	public static void main(String[] args) {
		new VacationCalculation();
	}
}

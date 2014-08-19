package org.lunding;

import java.math.BigDecimal;

public class VacationCalculation {
	
	public VacationCalculation(){
		ExchangeRates.initialize();
		new MenuFrame();
		//ExchangeRates.printXML(ExchangeRates.WEB);
		/*Event e = new Event("O-ringen", ExchangeRates.getCurrency("DKK"));
		Person rasmus = new Person("Rasmus", "dr@dr.dk");
		Person lars = new Person("Lars",  "dr@dr.dk");
		Person edsen = new Person("Edsen", "dr@dr.dk");
		Person laage = new Person("Laage", "dr@dr.dk");
		Person mikkel = new Person("Mikkel", "dr@dr.dk");
		Person eskil = new Person("Eskil", "dr@dr.dk");
		e.addPerson(rasmus);
		e.addPerson(lars);
		e.addPerson(edsen);
		e.addPerson(laage);
		e.addPerson(mikkel);
		e.addPerson(eskil);
		
		e.addExpense(new Expense("Mad", edsen, new BigDecimal("413.41"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("Vodka", eskil, new BigDecimal(2*57), ExchangeRates.getCurrency("DKK")));
		e.addExpense(new Expense("Alkohol", eskil, new BigDecimal("247"), ExchangeRates.getCurrency("DKK")));
		e.addExpense(new Expense("Pavilion", eskil, new BigDecimal("108"), ExchangeRates.getCurrency("DKK")));
		e.addExpense(new Expense("Mad", rasmus, new BigDecimal("422.50"), ExchangeRates.getCurrency("DKK")));
		e.addExpense(new Expense("Mad", rasmus, new BigDecimal("100"), ExchangeRates.getCurrency("DKK")));
		e.addExpense(new Expense("Mad", laage, new BigDecimal("420"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("Mad", mikkel, new BigDecimal("533.30"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("Pizza", mikkel, new BigDecimal("355"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("Grill", mikkel, new BigDecimal("117.5"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("ukendt", mikkel, new BigDecimal("147"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("Telt", lars, new BigDecimal("1305"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("Mad", lars, new BigDecimal("794.15"), ExchangeRates.getCurrency("SEK")));
		e.addExpense(new Expense("Sprut", lars, new BigDecimal("359.65"), ExchangeRates.getCurrency("DKK")));
		e.calculateWhoPayWho();
		Serializer.serializeEvent(e, "oringen");
		//Event event = Serializer.deserialzeEvent();
		//event.calculateWhoPayWho();*/
	}

	public static void main(String[] args) {
		new VacationCalculation();
	}
}

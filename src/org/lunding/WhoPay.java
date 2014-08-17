package org.lunding;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class is used to make "WhoPay"-objects. These objects have information about:<br>
 * <ul>
 * 	<li>The payer</li>
 * 	<li>The receiver</li>
 * 	<li>amount</li>
 * 	<li>currency</li>
 * </ul>
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class WhoPay implements Serializable{
	
	private Person payer;
	private Person receiver;
	private BigDecimal amount;
	private Currency currency;
	
	
	public WhoPay(Person payer, Person receiver, BigDecimal amount, Currency currency) {
		super();
		this.payer = payer;
		this.receiver = receiver;
		this.amount = amount;
		this.currency = currency;
	}

	public Person getPayer() {
		return payer;
	}

	public Person getReceiver() {
		return receiver;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public Currency getCurrency(){
		return currency;
	}

	@Override
	public String toString() {
		return ">" + payer.getName() + " have to pay " + amount.setScale(2, RoundingMode.HALF_UP) + " " + currency.getCode() + " to " + receiver.getName();
	}
}

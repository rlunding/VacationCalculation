package org.lunding;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Currency object is used to hold information about different currencies.
 * The hold three different variables: code, name and rate. This could e.g. be:
 * code = DKK, name = Dansk krone, rate = 100.
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class Currency implements Serializable{
	
	private String code;
	private String name;
	private BigDecimal rate;

	/**
	 * To create a new currency object, three parameters i needed.
	 * @param code usually three upper case letters, (e.g. EUR)
	 * @param name of the currency, (e.g. Euro)
	 * @param rate of the currency, (e.g. 754)
	 */
	public Currency(String code, String name, BigDecimal rate) {
		super();
		this.code = code;
		this.name = name;
		this.rate = rate.setScale(2, RoundingMode.HALF_UP);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getRate() {
		return rate;
	}
	
	public void setRate(BigDecimal rate){
		this.rate = rate;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(!(obj instanceof Currency)){
			return false;
		}
		Currency c = (Currency) obj;
		return c.code.equals(this.code); //&& c.name.equals(this.name);
	}

	@Override
	public String toString() {
		return "code:" + code + " name:" + name + " rate:" + rate;
	}	
}

package org.lunding;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Currency {
	
	private String code;
	private String name;
	private BigDecimal rate;

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

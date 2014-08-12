package org.lunding;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ExchangeRates {
	
	private ArrayList<Currency> currency;
	//Make a last updated value
	//Lav noget så den sidste opdatering bliver gemt i en fil.
	
	public ExchangeRates(){
		currency = new ArrayList<Currency>();
		updateCurrency();
	}
	
	public ExchangeRates(ArrayList<Currency> currency){
		this.currency = currency;
	}
	
	public ArrayList<Currency>getCurrencies(){
		return this.currency;
	}
	
	public Currency getCurrency(String code){
		for(Currency c : this.currency){
			if(c.getCode().equals(code)){
				return c;
			}
		}
		return null;
	}
	
	public void printXML(){
		writeXMLConsole(getDocument());
	}
	
	public void printCurrencies(){
		for(Currency c : this.currency){
			System.out.println(c.toString());
		}		
	}
	
	private void updateCurrency(){
		this.currency.add(new Currency("DKK", "Dansk krone", new BigDecimal(100)));
		NumberFormat nf = NumberFormat.getInstance();
		Document doc = getDocumentFromFile();
		Element root = doc.getRootElement();
		List<Element> elements = root.getChildren();
		List<Element> currency = elements.get(0).getChildren("currency");
		for(Element e : currency){
			String code = e.getAttributeValue("code");
			String desc = e.getAttributeValue("desc");
			String sRate = e.getAttributeValue("rate");
			BigDecimal rate = new BigDecimal("0");
			try {
				rate = new BigDecimal(nf.parse(sRate).doubleValue());
			} catch (ParseException e1) {
				System.err.println("Error parsing rate");
			}
			if(!code.isEmpty() && !desc.isEmpty() && rate.compareTo(BigDecimal.ZERO) != 0){
				Currency c = new Currency(code, desc, rate);
				if(this.currency.contains(c)){
					this.currency.get(this.currency.indexOf(c)).setRate(rate);
					System.out.println(desc + " updated");
				} else {
					this.currency.add(c);
				}
			}
		}
		//printCurrencies();
	}
	
	private Document getDocument(){
		String url = "http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=da";
		Document doc;
		try {
			doc = (Document) new SAXBuilder().build(new URL(url));
			return doc;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Document getDocumentFromFile(){
		Document doc;
		try {
			doc = (Document) new SAXBuilder().build(new File("currency.xml"));
			return doc;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void writeXMLConsole(Document doc){
		XMLOutputter xmlout = new XMLOutputter();
		Format f = Format.getPrettyFormat();
		xmlout.setFormat(f);
		try {
			xmlout.output(doc, System.out);
		} catch (IOException e) {
			System.err.println("An error occurred when trying to print xml to console.");
		}
	}
}

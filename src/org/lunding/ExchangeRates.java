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
	
	private static ArrayList<Currency> currencyList;
	private static String lastUpdated;
	//Make a last updated value
	//Lav noget så den sidste opdatering bliver gemt i en fil.
	
	private ExchangeRates(){
	}
	
	public static void initialize(){
		lastUpdated = "";
		currencyList = new ArrayList<Currency>();
		updateCurrency(getDocumentFromFile());
	}
	
	public static void update(){
		updateCurrency(getDocumentFromWeb());
	}
	
	public static String lastUpdated(){
		return lastUpdated;
	}
	
	public static ArrayList<Currency>getCurrencies(){
		return currencyList;
	}
	
	public static Currency getCurrency(String code){
		for(Currency c : currencyList){
			if(c.getCode().equals(code)){
				return c;
			}
		}
		return null;
	}
	
	private static void updateCurrency(Document doc){
		currencyList.add(new Currency("DKK", "Dansk krone", new BigDecimal(100)));
		NumberFormat nf = NumberFormat.getInstance();
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
				if(currency.contains(c)){
					currencyList.get(currency.indexOf(c)).setRate(rate);
					System.out.println(desc + " updated");
				} else {
					currencyList.add(c);
				}
			}
		}
		lastUpdated = elements.get(0).getAttributeValue("id");
		//printCurrencies();
	}
	
	private static Document getDocumentFromWeb(){
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
	
	private static Document getDocumentFromFile(){
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
	
	public static void printXML(String source){
		if(source.equalsIgnoreCase("WEB")){
			writeXMLConsole(getDocumentFromWeb());
		} else if(source.equalsIgnoreCase("FILE")){
			writeXMLConsole(getDocumentFromFile());
		} else {
			System.out.println("The document is only available from: FILE or WEB");
		}		
	}
	
	public static void printCurrencies(){
		for(Currency c : currencyList){
			System.out.println(c.toString());
		}		
	}
	
	private static void writeXMLConsole(Document doc){
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

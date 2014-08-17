package org.lunding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

/**
 * This is a static class made for containing and updating currency. 
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class ExchangeRates {
	
	//Public constants
	public static final String WEB = "WEB";
	public static final String FILE = "FILE";
	
	private static ArrayList<Currency> currencyList;
	private static String lastUpdated;
	//Lav noget så den sidste opdatering bliver gemt i en fil.
	
	private ExchangeRates(){
		
	}
	
	/**
	 * This method have to be called once before the class will work properly.
	 */
	public static void initialize(){
		lastUpdated = "";
		currencyList = new ArrayList<Currency>();
		updateCurrency(getDocumentFromFile());
	}
	
	/**
	 * Try to update the currency list from the web.
	 */
	public static void update(){
		Document doc = getDocumentFromWeb();
		if(doc != null){
			updateCurrency(doc);
			writeXMLFile(doc, "currency1");
		}
	}
	
	/**
	 * Return a string with the date of when the currency list was updated last time
	 * @return String lastupdated
	 */
	public static String lastUpdated(){
		return lastUpdated;
	}
	
	/**
	 * Get a list of all currencies
	 * @return ArrayList<Currency> with all currencies
	 */
	public static ArrayList<Currency> getCurrencies(){
		return currencyList;
	}
	
	/**
	 * Get a specific currency by its code. (e.g. DKK)
	 * @param code
	 * @return Currency with that corresponding code or null
	 */
	public static Currency getCurrency(String code){
		for(Currency c : currencyList){
			if(c.getCode().equals(code)){
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Update the currency list and lastupdated value.<br>
	 * The input parameter should be an XML document containing the values of different currencies<br>
	 * with the "Danske Krone" as the reference (value = 100).
	 * @param Document doc
	 */
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
				System.err.println("Error parsing rate for: " + code);
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
	
	/**
	 * Retrieve an xml-document with currencies from the Danish National Bank.
	 * @return Document (xml)
	 */
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
	
	/**
	 * Retrieve an xml-document with currencies from the local disk.
	 * @return Document (xml)
	 */
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
	
	/**
	 * Print all currencies into the console.
	 */
	public static void printCurrencies(){
		for(Currency c : currencyList){
			System.out.println(c.toString());
		}		
	}
	
	/**
	 * Print the raw xml into the console.<br>
	 * The source either have to be from WEB or FILE.
	 * @param source
	 */
	public static void printXML(String source){
		if(source.equalsIgnoreCase(WEB)){
			writeXMLConsole(getDocumentFromWeb());
		} else if(source.equalsIgnoreCase(FILE)){
			writeXMLConsole(getDocumentFromFile());
		} else {
			System.out.println("The document is only available from: FILE or WEB");
		}		
	}
	
	/**
	 * Private helper method to print xml out in the console
	 * @param doc
	 */
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
	
	/**
	 * Private helper method to write xml to an file.
	 * @param doc
	 * @param filename
	 */
	private static void writeXMLFile(Document doc, String filename){
		XMLOutputter xmlout = new XMLOutputter();
		Format f = Format.getPrettyFormat();
		xmlout.setFormat(f);
		try {
			xmlout.output(doc, new FileOutputStream(filename + ".xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

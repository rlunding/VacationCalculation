package org.lunding;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

/**
 * Utilities class<br>
 * Made for small methods that can be used multiple places.
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
public class MyUtilities {
	
	private MyUtilities(){
		
	}
	
	/**
	 * Create a border with a title (the string input).<br>
	 * The border can be added to a JPanel.
	 * @param title
	 * @return Border
	 */
	public static TitledBorder border(String title){
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), title);
		border.setTitleJustification(TitledBorder.ABOVE_TOP);
		return border;
	}
	
	/**
	 * Validate that a string is an email.
	 * @param email
	 * @return boolean
	 */
	public static boolean validateEmail(String email){
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}

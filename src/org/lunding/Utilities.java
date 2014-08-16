package org.lunding;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class Utilities {
	
	private Utilities(){
		
	}
	
	public static TitledBorder border(String title){
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), title);
		border.setTitleJustification(TitledBorder.ABOVE_TOP);
		return border;
	}
	
	public static boolean validateEmail(String email){
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}

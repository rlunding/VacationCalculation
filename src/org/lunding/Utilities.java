package org.lunding;

import java.awt.Color;

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

}

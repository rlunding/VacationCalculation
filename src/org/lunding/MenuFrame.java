package org.lunding;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuFrame extends JFrame{
	private static final long serialVersionUID = 4265417303406794381L;

	private JButton selectEvent;
	private JButton createEvent;
	private JButton createUser;
	
	public MenuFrame(){
		setLayout(new GridLayout(3,1));
		selectEvent = new JButton("Select event");
		createEvent = new JButton("Create event");
		createUser = new JButton("Create user");
		
		selectEvent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//new pick event
				//close this window
				System.out.println("Hej");
			}
		});
		createEvent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//new pick event
				//close this window
				System.out.println("Hej");
			}
		});
		createUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//new pick event
				//close this window
				System.out.println("Hej");
			}
		});
		
		
		add(selectEvent);
		add(createEvent);
		add(createUser);
		
		setBackground(Color.WHITE);
		setTitle("Vacation Calculation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

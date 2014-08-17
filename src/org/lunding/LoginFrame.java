package org.lunding;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class it not used anymore.
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
@Deprecated
public class LoginFrame extends JFrame{

	private JTextField user;
	private JTextField password;
	private JButton login;
	
	public LoginFrame(){
		user = new JTextField();
		password = new JTextField();
		login = new JButton("Login");
		
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(user.getText().equals("admin") && password.getText().equals("admin")){
					new MenuFrame();
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Wrong login details");
				}
			}
		});
		
		setLayout(new GridLayout(3,1));

		add(user);
		add(password);
		add(login);
		setBackground(Color.WHITE);
		setTitle("Vacation Calculation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

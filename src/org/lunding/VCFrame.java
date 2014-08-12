package org.lunding;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VCFrame extends JFrame{
	private static final long serialVersionUID = -5038429981833850953L;

	private Event event;
	
	public VCFrame(Event event){
		this.event = event;
		add(personPanel());
		
		setBackground(Color.WHITE);
		setTitle("Vacation Calculation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel personPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		
		final JComboBox persons = new JComboBox(event.getPersons().toArray());
		JButton submitDelete = new JButton("delete person");
		submitDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Person person = (Person) persons.getSelectedItem();
				event.removePerson(person);
				persons.removeItem(person);
			}
		});
		
		final JTextField nameField = new JTextField();
		final JTextField emailField = new JTextField();
		JButton submitCreate = new JButton("Create person");
		
		submitCreate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				String email = emailField.getText();
				Person person = new Person(name, email);
				event.addPerson(person);
				persons.addItem(person);
				nameField.setText("");
				emailField.setText("");
			}
		});
		
		panel.add(nameField);
		panel.add(emailField);
		panel.add(submitCreate);
		panel.add(persons);
		panel.add(submitDelete);
		
		return panel;
	}

}

package org.lunding;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VCFrame extends JFrame{

	private Event event;
	private final JComboBox personBox;
	private JTextArea expensesView;
	private JTextArea whoPayWhoView;
	
	public VCFrame(Event event){
		personBox = new JComboBox(event.getPersons().toArray());
		this.event = event;
		setLayout(new GridLayout(2,2));
		add(personPanel());
		add(whoPayPanel());
		add(expensePanel());
		add(expenseViewPanel());
		expenseAdded();
		
		setBackground(Color.WHITE);
		setTitle("Vacation Calculation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void expenseAdded(){
		expensesView.setText("");
		StringBuilder sb = new StringBuilder();
		for(Expense exp : event.getExpenses()){
			sb.append(exp.toString() + "\n");
		}
		expensesView.append(sb.toString());
		
		whoPayWhoView.setText("");
		StringBuilder sb2 = new StringBuilder();
		for(WhoPay wp : event.calculateWhoPayWho()){
			sb2.append(wp.toString() + "\n");
		}
		whoPayWhoView.append(sb2.toString());
	}
	
	private JPanel whoPayPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(Utilities.border("Who pay"));
		panel.setLayout(new GridLayout(1,1));
		
		whoPayWhoView = new JTextArea();
		JScrollPane scrollpane = new JScrollPane(whoPayWhoView);
		panel.add(scrollpane);
		
		return panel;
	}
	
	private JPanel expenseViewPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(Utilities.border("Expenses"));
		panel.setLayout(new GridLayout(1,1));
		
		expensesView = new JTextArea();
		JScrollPane scrollpane = new JScrollPane(expensesView);
		
		panel.add(scrollpane);
		return panel;
	}
	
	private JPanel expensePanel(){
		JPanel panel = new JPanel();
		panel.setBorder(Utilities.border("Create new expense"));
		panel.setLayout(new GridLayout(5,1));
		
		final JTextField titleField = new JTextField();
		//final JComboBox personBox = new JComboBox(event.getPersons().toArray());
		final JTextField amountField = new JTextField();
		final JComboBox currencyBox = new JComboBox(ExchangeRates.getCurrencies().toArray());
		JButton submit = new JButton("Submit expense");
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleField.getText();
				Person person = (Person) personBox.getSelectedItem();
				Currency currency = (Currency) currencyBox.getSelectedItem();
				BigDecimal amount;
				try { 
					amount = new BigDecimal(Double.parseDouble(amountField.getText()));
				} catch (Exception exc){
					JOptionPane.showMessageDialog(amountField, "Amount have to be a number");
					return;
				}
				event.addExpense(new Expense(title, person, amount, currency));
				expenseAdded();
			}
		});
		
		panel.add(titleField);
		panel.add(personBox);
		panel.add(amountField);
		panel.add(currencyBox);
		panel.add(submit);
		
		return panel;
	}
	
	private JPanel personPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(Utilities.border("Persons"));
		panel.setLayout(new GridLayout(2,1));
		
		JPanel deletePanel = new JPanel();
		deletePanel.setBorder(Utilities.border("Delete person"));
		deletePanel.setLayout(new GridLayout(2,1));
		JPanel createPanel = new JPanel();
		createPanel.setBorder(Utilities.border("Create person"));
		createPanel.setLayout(new GridLayout(3,1));
		
		
		//final JComboBox persons = new JComboBox(event.getPersons().toArray());
		final JComboBox persons = new JComboBox(personBox.getModel());
		JButton submitDelete = new JButton("delete person");
		submitDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Person person = (Person) persons.getSelectedItem();
				event.removePerson(person);
				persons.removeItem(person);
				expenseAdded();
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
				expenseAdded();
			}
		});
		
		createPanel.add(nameField);
		createPanel.add(emailField);
		createPanel.add(submitCreate);
		deletePanel.add(persons);
		deletePanel.add(submitDelete);
		panel.add(createPanel);
		panel.add(deletePanel);
		
		return panel;
	}

}

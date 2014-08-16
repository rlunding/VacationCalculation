package org.lunding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VCFrame extends JFrame{

	private Event event;
	private final JComboBox personBox;
	private JTextArea expensesView;
	private JTextArea whoPayWhoView;
	
	public VCFrame(Event event){
		this.event = event;
		setLayout(new BorderLayout());
		personBox = new JComboBox(event.getPersons().toArray());
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(2,2));
		panelCenter.add(personPanel());
		panelCenter.add(whoPayPanel());
		panelCenter.add(expensePanel());
		panelCenter.add(expenseViewPanel());
		add(panelCenter, BorderLayout.CENTER);
		expenseAdded();
		
		add(bottomPanel(), BorderLayout.SOUTH);
		
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
		ArrayList<WhoPay> whoPayList = event.calculateWhoPayWho();
		if(whoPayList == null){
			return;
		}
		for(WhoPay wp : whoPayList){
			sb2.append(wp.toString() + "\n");
		}
		whoPayWhoView.append(sb2.toString());
	}
	
	private JPanel bottomPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		JButton save = new JButton("Save");
		JButton exit = new JButton("Exit");
		
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog(null, "File name? (3-15 characters");
				if(name.isEmpty() || name.length() < 3 || name.length() > 15 || !name.matches("[a-zA-Z]+")){
					JOptionPane.showMessageDialog(null, "Invalid filename");
					return;
				}
				if(Serializer.serializeEvent(event, name)){
					JOptionPane.showMessageDialog(null, "Succes!");
				} else {
					JOptionPane.showMessageDialog(null, "An error occurred...");
				}
			}
		});
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Exit without saving?", "Exit", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	new MenuFrame();
		        	dispose();
		        }
		        else {
		           //do nothing? Perhaps show some save dialog
		        }
			}
		});
		
		panel.add(exit);
		panel.add(save);
		
		return panel;
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
		panel.setLayout(new GridLayout(5,2));
		
		JLabel titleLabel = new JLabel("Title: ", SwingConstants.RIGHT);
		JLabel personLabel = new JLabel("Payer: ", SwingConstants.RIGHT);
		JLabel amountLabel = new JLabel("amount: ", SwingConstants.RIGHT);
		JLabel currencyLabel = new JLabel("Currency: ", SwingConstants.RIGHT);
		final JTextField titleField = new JTextField();
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
				if(title.isEmpty() || title.length() < 3 || title.length() > 15){
					JOptionPane.showMessageDialog(titleField, "Please write a title (3-15 characters)");
					return;
				}
				if(person == null || !event.getPersons().contains(person)){
					JOptionPane.showMessageDialog(personBox, "Please choose a person");
					return;
				}
				if(currency == null || !ExchangeRates.getCurrencies().contains(currency)){
					JOptionPane.showMessageDialog(currencyBox, "Please choose a currency");
					return;
				}
				
				event.addExpense(new Expense(title, person, amount, currency));
				expenseAdded();
			}
		});
		
		panel.add(titleLabel);
		panel.add(titleField);
		panel.add(personLabel);
		panel.add(personBox);
		panel.add(amountLabel);
		panel.add(amountField);
		panel.add(currencyLabel);
		panel.add(currencyBox);
		panel.add(new JPanel());
		panel.add(submit);
		
		return panel;
	}
	
	private JPanel personPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(Utilities.border("Persons"));
		panel.setLayout(new GridLayout(2,1));
		
		JPanel deletePanel = new JPanel();
		deletePanel.setBorder(Utilities.border("Delete person"));
		deletePanel.setLayout(new GridLayout(2,2));
		JPanel createPanel = new JPanel();
		createPanel.setBorder(Utilities.border("Create person"));
		createPanel.setLayout(new GridLayout(3,2));
		
		JLabel personLabel = new JLabel("Person: ", SwingConstants.RIGHT);
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
		
		JLabel nameLabel = new JLabel("Name: ", SwingConstants.RIGHT);
		JLabel emailLabel = new JLabel("Email: ", SwingConstants.RIGHT);
		final JTextField nameField = new JTextField();
		final JTextField emailField = new JTextField();
		JButton submitCreate = new JButton("Create person");
		
		submitCreate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				String email = emailField.getText();
				if(name.isEmpty() || name.length() < 2 || name.length() > 20){
					JOptionPane.showMessageDialog(nameField, "Please write a name");
					return;
				}
				if(email.isEmpty() || !Utilities.validateEmail(email)){
					JOptionPane.showMessageDialog(emailField, "Please write a email");
					return;
				}				
				Person person = new Person(name, email);
				event.addPerson(person);
				persons.addItem(person);
				nameField.setText("");
				emailField.setText("");
				expenseAdded();
			}
		});
		
		createPanel.add(nameLabel);
		createPanel.add(nameField);
		createPanel.add(emailLabel);
		createPanel.add(emailField);
		createPanel.add(new JPanel());
		createPanel.add(submitCreate);
		deletePanel.add(personLabel);
		deletePanel.add(persons);
		deletePanel.add(new JPanel());
		deletePanel.add(submitDelete);
		panel.add(createPanel);
		panel.add(deletePanel);
		
		return panel;
	}

}

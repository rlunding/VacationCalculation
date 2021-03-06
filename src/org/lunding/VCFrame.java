package org.lunding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

/**
 * This class extends a JFrame an is used as a GUI to manipulate and event.
 * <ul>
 * 	<li>Create/delete persons</li>
 * 	<li>Create an expense</li>
 * 	<li>See who is paying who</li>
 * 	<li>See all expenses</li>
 * </ul>
 * @author Rasmus Lunding
 * @version 1.0
 * @since 2014-08-10
 */
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
		
		expensesView.setText("");
		StringBuilder sb = new StringBuilder();
		for(Expense exp : event.getExpenses()){
			sb.append(exp.toString() + "\n");
		}
		sb.append("\nTotal expenses: " + event.getTotalExpenses() + " " + event.getCurrency().getCode() +"\n");
		sb.append("Price pr person: " + event.getPricePrPerson() + " " + event.getCurrency().getCode());
		expensesView.append(sb.toString());
		
		//System.out.println(event.getConsoleTrace());
	}
	
	private JPanel bottomPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		
		final JLabel currencyLabel = new JLabel(ExchangeRates.lastUpdated(), SwingConstants.RIGHT);
		JButton save = new JButton("Save");
		JButton exit = new JButton("Exit");
		JButton updateCurrency = new JButton("update currency");
		
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
		updateCurrency.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ExchangeRates.update();
				currencyLabel.setText(ExchangeRates.lastUpdated());
				expenseAdded();
			}
		});
		
		panel.add(currencyLabel);
		panel.add(updateCurrency);
		panel.add(exit);
		panel.add(save);
		
		return panel;
	}
	
	private JPanel whoPayPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(MyUtilities.border("Who pay"));
		panel.setLayout(new GridLayout(1,1));
		
		whoPayWhoView = new JTextArea();
		JScrollPane scrollpane = new JScrollPane(whoPayWhoView);
		panel.add(scrollpane);
		
		return panel;
	}
	
	private JPanel expenseViewPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(MyUtilities.border("Expenses"));
		panel.setLayout(new GridLayout(1,1));
		
		expensesView = new JTextArea();
		JScrollPane scrollpane = new JScrollPane(expensesView);
		expensesView.setEditable(false);
		
		expensesView.addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseClicked(MouseEvent e) {
	            if (e.getButton() != MouseEvent.BUTTON1) {
	               return;
	            }
	            if (e.getClickCount() != 2) {
	               return;
	            }

	            int offset = expensesView.viewToModel(e.getPoint());

	            try {
	            	//Find marked text
	               int rowStart = Utilities.getRowStart(expensesView, offset);
	               int rowEnd = Utilities.getRowEnd(expensesView, offset);
	               String selectedLine = expensesView.getText().substring(rowStart, rowEnd);
	               
	               //Mark the text
	               expensesView.setSelectionStart(rowStart);
	               expensesView.setSelectionEnd(rowEnd);
	               
	               //calculate row
	               int caretPos = expensesView.getCaretPosition();
	               int rowNum = (caretPos == 0) ? 1 : 0;
	               for(int i = caretPos; i > 0; rowNum++){
	            	   i = Utilities.getRowStart(expensesView, i) - 1;
	               }
	               System.out.println("Row: " + rowNum);
	               System.out.println(selectedLine);
	               
	               int reply = JOptionPane.showConfirmDialog(null, "Delete expense?", "Delete", JOptionPane.YES_NO_OPTION);
			       if (reply == JOptionPane.YES_OPTION) {
			    	   ArrayList<Expense> expenses = event.getExpenses();
			    	   if(expenses.size() > rowNum-1){
			    		   event.removeExpense(expenses.get(rowNum-1));
			    		   expenseAdded();
			    	   } 
			       }
			       else {
			          //do nothing?
			       }

	            } catch (BadLocationException e1) {
	               e1.printStackTrace();
	            }
	         }
	      });
		
		panel.add(scrollpane);
		return panel;
	}
	
	private JPanel expensePanel(){
		JPanel panel = new JPanel();
		panel.setBorder(MyUtilities.border("Create new expense"));
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
		panel.setBorder(MyUtilities.border("Persons"));
		panel.setLayout(new GridLayout(2,1));
		
		JPanel deletePanel = new JPanel();
		deletePanel.setBorder(MyUtilities.border("Delete person"));
		deletePanel.setLayout(new GridLayout(2,2));
		JPanel createPanel = new JPanel();
		createPanel.setBorder(MyUtilities.border("Create person"));
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
				if(email.isEmpty() || !MyUtilities.validateEmail(email)){
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

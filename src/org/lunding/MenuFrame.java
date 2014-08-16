package org.lunding;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuFrame extends JFrame{

	private JPanel createPanel;
	private JPanel initialPanel;
	
	public MenuFrame(){
		setLayout(new GridLayout(2,1));
		initialPanel = initialPanel();
		createPanel = createPanel();
		add(initialPanel);
		add(createPanel);
		
		createPanel.setVisible(false);
		
		
		setBackground(Color.WHITE);
		setTitle("Vacation Calculation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel createPanel(){
		//Create panel and prepare it
		JPanel panel = new JPanel();
		panel.setBorder(Utilities.border("Create new event"));
		panel.setLayout(new GridLayout(3,2));
		
		//Initialize elements for currency-combobox
		ArrayList<Currency> currencyList = ExchangeRates.getCurrencies();
		String[] currencyStrings = new String[currencyList.size()];
		for(int i = 0; i < currencyList.size(); i++){
			currencyStrings[i] = currencyList.get(i).getCode();
		}
		
		//Create elements
		JLabel nameLabel = new JLabel("Event name: ", SwingConstants.RIGHT);
		JLabel currencyLabel = new JLabel("Currency: ", SwingConstants.RIGHT);
		final JTextField eventName = new JTextField();
		final JComboBox currency = new JComboBox(currencyList.toArray());
		JButton submit = new JButton("Create event");
		
		//Add Actionlisteners
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//Take information and go to event page.
				String name = eventName.getText();
				Currency cur = (Currency) currency.getSelectedItem();
				if(name.isEmpty() || name.length() < 2 || name.length() > 20){
					JOptionPane.showMessageDialog(eventName, "Please write a name (2-20 characters)");
					return;
				}
				Event event = new Event(name, cur);
				new VCFrame(event);
				dispose();
				//send event to new JFrame
			}
		});
		
		//Add elements to panel and return it
		panel.add(nameLabel);
		panel.add(eventName);
		panel.add(currencyLabel);
		panel.add(currency);
		panel.add(new JPanel());
		panel.add(submit);
		return panel;
	}
	
	private JPanel initialPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(Utilities.border("Select or create event"));
		panel.setLayout(new GridLayout(2,1));
		JButton selectEvent = new JButton("Select event");
		JButton createEvent = new JButton("Create event");
		
		selectEvent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//select event from file
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("event", "event");
				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int returnVal = chooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION){
					 System.out.println("You chose to open this file: " +
						        chooser.getSelectedFile().getName());
					 Event event = Serializer.deserialzeEvent(chooser.getSelectedFile());
					 new VCFrame(event);
				}
				dispose();
			}
		});
		createEvent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				createPanel.setVisible(true);
				enableComponents(initialPanel, false);
			}
		});		
		
		panel.add(selectEvent);
		panel.add(createEvent);
		
		return panel;
	}
	
	private void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }
}

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.text.*;

//test

public class GUIWindow  extends JFrame{
	
	private boolean onEmployees = true;
	private boolean addingPerson = true;
	
	private int lastScheduleIndex = -1;
	private int currentScheduleIndex = -1;
	private int lastPersonIndex = -1;
	private int currentPersonIndex = -1;
	
	private Manager m = new Manager();
	
	private JLabel blankSpace = new JLabel(" ");
	
	//switch between customers and employees
	private JButton switchBetweenEmplyeesAndCustomersButton = new JButton("Switch to Customer View");
	private JButton saveButton = new JButton("Save");
	private JButton loadButton = new JButton("Load");
	
	//person info
	private JButton addPersonButton = new JButton("add");
	private JLabel nameLabel = new JLabel("Name");
	private JLabel payLabel = new JLabel("Pay");
	private JFormattedTextField nameField = new JFormattedTextField();
	private JFormattedTextField payField = new JFormattedTextField();
	
	//show/edit schedule
	private JButton addScheduleButton = new JButton("add");
	private JButton previousScheduleButton = new JButton("Previous");
	private JButton nextScheduleButton = new JButton("Next");
	private JLabel startHourLabel = new JLabel("Start hour");
	private JLabel startMinLabel = new JLabel("Start min");
	private JLabel endHourLabel = new JLabel("End hour");
	private JLabel endMinLabel = new JLabel("End min");
	private JLabel dayLabel = new JLabel("Day");
	private JFormattedTextField startHourField = new JFormattedTextField();
	private JFormattedTextField startMinField = new JFormattedTextField();
	private JFormattedTextField endHourField = new JFormattedTextField();
	private JFormattedTextField endMinField = new JFormattedTextField();
	private JFormattedTextField dayField = new JFormattedTextField();
	private JFormattedTextField scheduleField = new JFormattedTextField();
	
	
	//switch people
	private JButton firstPersonButton = new JButton("First");
	private JButton previousPersonButton = new JButton("Previous");
	private JButton nextPersonButton = new JButton("Next");
	private JButton lastPersonButton = new JButton("Last");
	
	public GUIWindow(){
		 
		JPanel employeeOrCustomerPanel = new JPanel(new GridLayout(4,1,3,3));
		
		employeeOrCustomerPanel.add(switchBetweenEmplyeesAndCustomersButton);
		employeeOrCustomerPanel.add(saveButton);
		employeeOrCustomerPanel.add(loadButton);
		
		
		JPanel personInfoPanel = new JPanel(new GridLayout(2,2,3,3));
		
			JPanel nameAndPayPanel = new JPanel(new GridLayout(2,2,3,3));
			nameAndPayPanel.add(nameLabel);
			nameAndPayPanel.add(payLabel);
			nameAndPayPanel.add(nameField);
			nameAndPayPanel.add(payField);
			
			personInfoPanel.add(nameAndPayPanel);
			personInfoPanel.add(addPersonButton);
			
		
		JPanel schedulePanel = new JPanel(new GridLayout(2,1,3,3));
		
				JPanel scheduleShowPanel = new JPanel(new GridLayout(2,1,3,3));
					
					scheduleShowPanel.add(scheduleField);
					
					JPanel scheduleShowChangePanel = new JPanel(new GridLayout(1,2,3,3));
						
						scheduleShowChangePanel.add(previousScheduleButton);
						scheduleShowChangePanel.add(nextScheduleButton);
					
					scheduleShowPanel.add( scheduleShowChangePanel);
					
				JPanel scheduleAddPanel = new JPanel(new GridLayout(2,1,3,3));
				
					JPanel scheduleEditConfirmPanel = new JPanel(new GridLayout(2,5,3,3));
						
						scheduleEditConfirmPanel.add(startHourLabel);
						scheduleEditConfirmPanel.add(startMinLabel);
						scheduleEditConfirmPanel.add(endHourLabel);
						scheduleEditConfirmPanel.add(endMinLabel);
						scheduleEditConfirmPanel.add(dayLabel);
						scheduleEditConfirmPanel.add(startHourField);
						scheduleEditConfirmPanel.add(startMinField);
						scheduleEditConfirmPanel.add(endHourField);
						scheduleEditConfirmPanel.add(endMinField);
						scheduleEditConfirmPanel.add(dayField);
						
				scheduleAddPanel.add(scheduleEditConfirmPanel);
				scheduleAddPanel.add(addScheduleButton);
		
		schedulePanel.add(scheduleShowPanel);		
		schedulePanel.add(scheduleAddPanel);	
					
		JPanel indexChangerPanel = new JPanel(new GridLayout(1,4,3,3));
		
		indexChangerPanel.add(firstPersonButton);
		indexChangerPanel.add(previousPersonButton);
		indexChangerPanel.add(nextPersonButton);
		indexChangerPanel.add(lastPersonButton);
		
		
		JPanel layout = new JPanel(new GridLayout(4,1,3,3));
		layout.add(employeeOrCustomerPanel);
		layout.add(personInfoPanel);
		layout.add(schedulePanel);
		layout.add(indexChangerPanel);
		
		
		Container container = getContentPane();
		container.add(layout, BorderLayout.CENTER);
		
		switchBetweenEmplyeesAndCustomersButton.addActionListener(new switchBetweenEmplyeesAndCustomersListener());
		saveButton.addActionListener(new saveListener());
		loadButton.addActionListener(new loadListener());
		
		addPersonButton.addActionListener(new addPersonListener());
		
		addScheduleButton.addActionListener(new addScheduleListener());
		previousScheduleButton.addActionListener(new previousScheduleListener());
		nextScheduleButton.addActionListener(new nextScheduleListener());
		
		firstPersonButton.addActionListener(new firstPersonListener());
		previousPersonButton.addActionListener(new previousPersonListener());
		nextPersonButton.addActionListener(new nextPersonListener());
		lastPersonButton.addActionListener(new lastPersonListener());
		
		nameField.addActionListener(new nameListener());
		payField.addActionListener(new payListener());
		
		addPerson();
	}
		
	private void addPerson(){
		addPersonButton.setText("save");
		nameField.setText("");
		payField.setText("");
		if(!onEmployees){
			payField.setText("");
		}
		
		saveButton.setEnabled(false);
		
		startHourField.setText("");
		startMinField.setText("");
		endHourField.setText("");
		endMinField.setText("");
		dayField.setText("");
		scheduleField.setText("");
		
		addScheduleButton.setEnabled(false);
		previousScheduleButton.setEnabled(false);
		nextScheduleButton.setEnabled(false);
		
		startHourField.setEnabled(false);
		startMinField.setEnabled(false);
		endHourField.setEnabled(false);
		endMinField.setEnabled(false);
		dayField.setEnabled(false);
		scheduleField.setEnabled(false);
		
		firstPersonButton.setEnabled(false);
		previousPersonButton.setEnabled(false);
		nextPersonButton.setEnabled(false);
		lastPersonButton.setEnabled(false);
		
		nameField.removeActionListener(new nameListener());
		payField.removeActionListener(new payListener());
	}
	
	private void savePerson(){
		
		boolean success = true;
		
		String name = nameField.getText();
		if(name.length() == 0){
			success = false;
			JOptionPane.showMessageDialog(null,"Please enter a name.","Incorrect Input",JOptionPane.WARNING_MESSAGE);
		}
		double pay = 0;
		if(onEmployees){
			try{
				pay = Double.parseDouble(payField.getText());
			}
			catch(Exception e){
				success = false;
				JOptionPane.showMessageDialog(null,"Please enter pay as a number.","Incorrect Input",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if(success){
			if(onEmployees){
				m.addEmployee(new Employee(pay,name));
			}
			else{
				m.addCustomer(new Person(name));
			}
			
			nameField.addActionListener(new nameListener());
			payField.addActionListener(new payListener());
			
			if(onEmployees){
				payField.setEnabled(true);
			}
			
			addingPerson = false;
			lastPersonIndex++;
			currentPersonIndex = lastPersonIndex;
			currentScheduleIndex = -1;
			
			addPersonButton.setText("add");
			
			saveButton.setEnabled(true);
			
			addScheduleButton.setEnabled(true);
			previousScheduleButton.setEnabled(true);
			nextScheduleButton.setEnabled(true);
			startHourField.setEnabled(true);
			startMinField.setEnabled(true);
			endHourField.setEnabled(true);
			endMinField.setEnabled(true);
			dayField.setEnabled(true);
			scheduleField.setEnabled(true);
			
			firstPersonButton.setEnabled(true);
			previousPersonButton.setEnabled(true);
			nextPersonButton.setEnabled(true);
			lastPersonButton.setEnabled(true);
		}
	}
	
	private void addSchedule(){
		boolean success = true;
		
		int startHour = 0;
		int startMin = 0;
		int endHour = 0;
		int endMin = 0;
		int day = 0;
		try{
			startHour = Integer.parseInt(startHourField.getText());
			startMin = Integer.parseInt(startMinField.getText());
			endHour = Integer.parseInt(endHourField.getText());
			endMin = Integer.parseInt(endMinField.getText());
			day = Integer.parseInt(dayField.getText());
		}
		catch(Exception e){
			success = false;
			JOptionPane.showMessageDialog(null,"Please only enter numbers for all fields.","Incorrect Input",JOptionPane.WARNING_MESSAGE);
		}
		if(success){
			if(startHour < 0 || startHour > 23){
				success = false;
				JOptionPane.showMessageDialog(null,"Start hour field: please only enter a number from 0-23","Incorrect Input",JOptionPane.WARNING_MESSAGE);
			}
			if(startMin < 0 || startMin > 59){
				success = false;
				JOptionPane.showMessageDialog(null,"Start min field: please only enter a number from 0-59","Incorrect Input",JOptionPane.WARNING_MESSAGE);
			}
			if(endHour < 0 || endHour > 23){
				success = false;
				JOptionPane.showMessageDialog(null,"End hour field: please only enter a number from 0-23","Incorrect Input",JOptionPane.WARNING_MESSAGE);
			}
			if(endMin < 0 || endMin > 59){
				success = false;
				JOptionPane.showMessageDialog(null,"End min field: please only enter a number from 0-59","Incorrect Input",JOptionPane.WARNING_MESSAGE);
			}
			if((startHour * 100 + startMin) > (endHour * 100 + endMin)){
				success = false;
				JOptionPane.showMessageDialog(null,"Please enter a end time after start time","Incorrect Input",JOptionPane.WARNING_MESSAGE);
			}
			if(day < 1 || day > 7){
				success = false;
				JOptionPane.showMessageDialog(null,"Day field: please only enter a number from 1-7","Incorrect Input",JOptionPane.WARNING_MESSAGE);
			}
			if(success){
				if(onEmployees == true){
					m.getEmployee(currentPersonIndex).addDateTime(new DateTime(startHour, startMin, endHour, endMin, day));
				}
				else{
					m.getCustomer(currentPersonIndex).addDateTime(new DateTime(startHour, startMin, endHour, endMin, day));
				}
				lastScheduleIndex++;
				currentScheduleIndex = lastScheduleIndex;
				setUpInfo();
			}
		}
	}
	
	private void setUpInfo(){
		if(onEmployees){
			nameField.setText(m.getEmployee(currentPersonIndex).getName());
			payField.setEnabled(true);
			payField.setText(m.getEmployee(currentPersonIndex).getPay() + "");
			lastScheduleIndex = m.getEmployee(currentPersonIndex).getScheduleLength() - 1;
			if(currentScheduleIndex <= lastScheduleIndex && currentScheduleIndex != -1){
				scheduleField.setText(m.getEmployee(currentPersonIndex).getSchedule(currentScheduleIndex).toString());
			}
			else{
				scheduleField.setText("");
			}
		}
		else{
			payField.setText("");
			payField.setEnabled(false);
			nameField.setText(m.getCustomer(currentPersonIndex).getName());
			lastScheduleIndex = m.getCustomer(currentPersonIndex).getScheduleLength() - 1;
			if(currentScheduleIndex <= lastScheduleIndex && currentScheduleIndex != -1){
				scheduleField.setText(m.getCustomer(currentPersonIndex).getSchedule(currentScheduleIndex).toString());
			}
			else{
				scheduleField.setText("");
			}
		}
	}
	
	private void resetToDefault() {
		switchBetweenEmplyeesAndCustomersButton.setText("Switch to Customer View");
		switchBetweenEmplyeesAndCustomersButton.setEnabled(true);
		saveButton.setText("Save");
		saveButton.setEnabled(true);
		loadButton.setText("Load");
		loadButton.setEnabled(true);
		
		addPersonButton.setText("add");
		addPersonButton.setEnabled(true);
		nameField.setText("");
		nameField.setEnabled(true);
		payField.setText("");
		payField.setEnabled(true);
		
		addScheduleButton = new JButton("add");
		previousScheduleButton = new JButton("Previous");
		nextScheduleButton = new JButton("Next");
		startHourField = new JFormattedTextField();
		startMinField = new JFormattedTextField();
		endHourField = new JFormattedTextField();
		endMinField = new JFormattedTextField();
		dayField = new JFormattedTextField();
		scheduleField = new JFormattedTextField();
		
		
		//switch people
		firstPersonButton.setText("First");
		previousPersonButton.setText("Previous");
		nextPersonButton.setText("Next");
		lastPersonButton.setText("Last");
	}
	
 	private class switchBetweenEmplyeesAndCustomersListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			currentScheduleIndex = 0;
			if(onEmployees == true){
				switchBetweenEmplyeesAndCustomersButton.setText("Switch to Employee View");
				onEmployees = false;
				lastPersonIndex = m.getCustomersSize() - 1;
				
				if(lastPersonIndex == -1){
					addingPerson = true;
					currentPersonIndex = -1;
					addPerson();
				}
				else{
					currentPersonIndex = 0;
					setUpInfo();
				}
				if(addingPerson){
					payField.setText("");
					payField.setEnabled(false);
				}
			}
			else{
				switchBetweenEmplyeesAndCustomersButton.setText("Switch to Customer View");
				resetToDefault();
				onEmployees = true;
				lastPersonIndex = m.getEmployeesSize() - 1;
				if(addingPerson){
					payField.setEnabled(true);
				}
				else{
					if(lastPersonIndex == -1){
						addingPerson = true;
						currentPersonIndex = -1;
						addPerson();
					}
					else{
						currentPersonIndex = 0;
						setUpInfo();
					}
				}
			}
			if(onEmployees && !addingPerson){
				if(m.getEmployee(currentPersonIndex).getScheduleLength() < 1){
					currentScheduleIndex = -1;
				}
			}
			else if(!addingPerson){
				if(m.getCustomer(currentPersonIndex).getScheduleLength() < 1){
					currentScheduleIndex = -1;
				}
			}
		}
	}
	
	private class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new JFileChooser();
			if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				try {
					m.save(chooser.getSelectedFile());					
				} 
				catch (FileNotFoundException f) {
					f.printStackTrace();
				}
			}
			else{
				//System.out.println("You chose not to open.");
			}
		}
	}
	
	private class loadListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new JFileChooser();
			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				try {
					Manager m = new Manager(chooser.getSelectedFile());
					lastPersonIndex = m.getEmployeesSize() - 1;
					if(lastPersonIndex == -1){
						onEmployees = false;
						lastPersonIndex = m.getCustomersSize() - 1;
						if(lastPersonIndex == -1){
							onEmployees = true;
							addingPerson = true;
							currentPersonIndex = -1;
							addPerson();
						}
					}
					else{
						currentPersonIndex = 0;
						setUpInfo();
					}
				} 
				catch (FileNotFoundException f) {
					f.printStackTrace();
				}
				catch (BadDataException f) {
					f.printStackTrace();
				}
			}
			else{
				//System.out.println("You chose not to open.");
			}
		}
	}

	private class addPersonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(!addingPerson){
				addingPerson = true;
				addPerson();
			}
			else{
				savePerson();
			}
		}
	}
	
	private class addScheduleListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			addSchedule();
		}
	}
	
	private class previousScheduleListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(currentScheduleIndex > 0){
				currentScheduleIndex--;
				setUpInfo();
			}
		}
	}
	
	private class nextScheduleListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(currentScheduleIndex < lastScheduleIndex){
				currentScheduleIndex++;
				setUpInfo();
			}
		}
	}
	
	private class firstPersonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			currentPersonIndex = 0;
			currentScheduleIndex = 0;
			setUpInfo();
		}
	}
	
	private class previousPersonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(currentPersonIndex > 0){
				currentPersonIndex--;
				currentScheduleIndex = 0;
				setUpInfo();
			}
		}
	}
	
	private class nextPersonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(currentPersonIndex < lastPersonIndex){
				currentPersonIndex++;
				currentScheduleIndex = 0;
				setUpInfo();
			}
		}
	}
	
	private class lastPersonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			currentPersonIndex = lastPersonIndex;
			currentScheduleIndex = 0;
			setUpInfo();
		}
	}

	private class nameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
		}
	}

	private class payListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
		}
	}
}

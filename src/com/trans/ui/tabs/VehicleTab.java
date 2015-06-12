package com.trans.ui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trans.dao.DriverDao;
import com.trans.dao.ModelDao;
import com.trans.dao.VehicleModelDao;
import com.trans.domain.Driver;
import com.trans.domain.Model;
import com.trans.domain.Vehicle;
import com.trans.ui.forms.ModelForm;
import com.trans.ui.forms.VehicleForm;
import com.trans.ui.tables.TransportTable;
import com.trans.ui.tables.VehicleTableModel;
import com.trans.util.ImageUtil;

/**
 * 
 * @author Lili
 */
public class VehicleTab extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LogManager.getLogger(VehicleTab.class.getName());

	private DriverDao driverDao;
	private VehicleModelDao vehicleDao;
	private ModelDao modelDao;
	
	private VehicleForm form;
	private TransportTable<Vehicle> table;
	
	private JScrollPane tableScrollPane;
	
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;
	
	private JButton btnSave;
	private JButton btnNewModel;
	
	// filters
	private JTextField driverFirstName;
	private JTextField driverLastName;
	private JButton btnFilter;
	private JButton btnShowAll;
	
	public VehicleTab(DriverDao driverDao, VehicleModelDao vehicleDao, ModelDao modelDao) {

		this.vehicleDao = vehicleDao;
		this.modelDao = modelDao;
		this.driverDao = driverDao;

		initializeComponents();
		addComponents();
		// load data
		refreshTable();
	}

	private void initializeComponents() {
		this.setLayout(new BorderLayout());

		form = new VehicleForm();
		refreshModelList();

		Vehicle vehicle = new Vehicle();
		form.setVehicle(vehicle);
		
		table = new TransportTable<Vehicle>(new VehicleTableModel());
		
		tableScrollPane = new JScrollPane();
		tableScrollPane.setPreferredSize(new Dimension(460, 200));
		tableScrollPane.setViewportView(table);
		tableScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		btnNew = new JButton("New", ImageUtil.createImageIcon("/res/add.png"));
		btnNew.addActionListener(new NewActionListener());

		btnEdit = new JButton("Edit", ImageUtil.createImageIcon("/res/edit.png"));
		btnEdit.addActionListener(new EditActionListener());

		btnDelete = new JButton("Delete", ImageUtil.createImageIcon("/res/remove.png"));
		btnDelete.addActionListener(new DeleteActionListener());

		btnSave = new JButton("Save", ImageUtil.createImageIcon("/res/ok.png"));
		btnSave.addActionListener(new SaveActionListener());
		
		btnNewModel = new JButton("Add new model...", ImageUtil.createImageIcon("/res/add.png"));
		btnNewModel.addActionListener( new NewModelActionListener() );

		// filters
		driverFirstName = new JTextField();
		driverFirstName.setColumns(12);

		driverLastName = new JTextField();
		driverLastName.setColumns(12);

		btnFilter = new JButton("Find", ImageUtil.createImageIcon("/res/find.png"));
		btnFilter.addActionListener(new FilterActionListener());
		
		btnShowAll = new JButton("Show All", ImageUtil.createImageIcon("/res/refresh.png"));
		btnShowAll.addActionListener(new ShowAllActionListener());
	}

	private void addComponents() {
		JPanel leftPanel = new JPanel(new BorderLayout());
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Vehicle");
		border.setTitleJustification(TitledBorder.LEFT);

		rightPanel.setBorder(border);
		
		JPanel leftPanelButtons = new JPanel(new FlowLayout());
		leftPanelButtons.add(btnNew);
		leftPanelButtons.add(btnEdit);
		leftPanelButtons.add(btnDelete);
		
		leftPanel.add(leftPanelButtons, BorderLayout.SOUTH);
		leftPanel.add(tableScrollPane, BorderLayout.CENTER);
		
		JPanel rightPanelButtons = new JPanel(new FlowLayout());
		rightPanelButtons.add(btnSave);
		rightPanelButtons.add(btnNewModel);
		
		rightPanel.add(form, BorderLayout.NORTH);
		rightPanel.add(rightPanelButtons, BorderLayout.SOUTH);
		
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		
		// filters
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JPanel firstNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		firstNamePanel.add(driverFirstName);
		JLabel firstNameLabel = new JLabel("Driver's First Name", JLabel.RIGHT);
		firstNameLabel.setLabelFor(driverFirstName);

		JPanel lastNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lastNamePanel.add(driverLastName);
		JLabel lastNameLabel = new JLabel("Last Name", JLabel.RIGHT);
		lastNameLabel.setLabelFor(driverLastName);

		filterPanel.add(firstNameLabel);
		filterPanel.add(firstNamePanel);
		filterPanel.add(lastNameLabel);
		filterPanel.add(lastNamePanel);
		filterPanel.add(btnFilter);
		filterPanel.add(btnShowAll);

		TitledBorder filterBorder = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Find vehicle by driver");
		filterBorder.setTitleJustification(TitledBorder.LEFT);

		filterPanel.setBorder(filterBorder);

		leftPanel.add(filterPanel, BorderLayout.NORTH);
	}

	private void clearFilter() {
		driverFirstName.setText("");
		driverLastName.setText("");
	}

	private void refreshModelList() {
		List<Model> modelList = modelDao.findAll();
		form.setModelList(modelList);
	}

	public void refreshTable() {
		List<Vehicle> vehicles = vehicleDao.findAll();
		LOG.info("vehicles size:" + vehicles.size());
		table.reload(vehicles);
	}
	private class ShowAllActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clearFilter();
			refreshTable();
		}
	}
	
	private class DeleteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Vehicle vehicleToDelete = table.getSelectedItem();
			vehicleDao.delete(vehicleToDelete.getId());
			refreshTable();
			JOptionPane.showMessageDialog(VehicleTab.this,
				    "Deleted vehicle " + vehicleToDelete.toString() + ".",
				    "Vehicle deleted",
				    JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class SaveActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			Vehicle selectedVehicle = form.getVehicle();
			
			try {
				if (selectedVehicle.getId() == 0) 
				{
					LOG.info("create");
					vehicleDao.create(selectedVehicle);
					JOptionPane.showMessageDialog(VehicleTab.this,
						    "Created vehicle " + selectedVehicle.toString() + ".",
						    "Vehicle created",
						    JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					LOG.info("update " + selectedVehicle.getVehicleNumber());
					vehicleDao.update(selectedVehicle);
					JOptionPane.showMessageDialog(VehicleTab.this,
						    "Updated vehicle " + selectedVehicle.toString() + ".",
						    "Vehicle updated",
						    JOptionPane.INFORMATION_MESSAGE);
				}
				clearFilter();
				refreshTable();
			} 
			catch (Exception e)
			{
				LOG.error(e.getMessage(), e);
				
				JOptionPane.showMessageDialog(VehicleTab.this,
				    "You have entered invalid data.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class NewModelActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			ModelForm modelForm = new ModelForm();
			int result = JOptionPane.showConfirmDialog(null, modelForm , 
		               "Add new model", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		      if (result == JOptionPane.OK_OPTION) {
		    	  
					Model newModel = new Model();
					newModel.setModel(modelForm.getModelName());
					newModel.setLoadCapacity(Integer.parseInt(modelForm.getLoadCapacity()));
					modelDao.create(newModel);
		         
					// refresh vehicle form
					refreshModelList();
		      }
			
		}
	}
	
	private class NewActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Vehicle vehicle = new Vehicle();
			form.setVehicle(vehicle);
		}
	}
	
	private class EditActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			form.setVehicle(table.getSelectedItem());
		}
	}
	
	private class FilterActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String firstName = driverFirstName.getText();
			String lastName = driverLastName.getText();
			
			Driver driver = driverDao.find(firstName, lastName);
			if (driver == null) {
				JOptionPane.showMessageDialog(VehicleTab.this,
						"Driver " + firstName + " " + lastName + " not found.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
			else {
				Vehicle vehicle = vehicleDao.findByNumber(driver.getVehicleNumber());
				if (vehicle == null)
				{
					JOptionPane.showMessageDialog(VehicleTab.this,
							"Vehicle not found.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					List<Vehicle> list = new ArrayList<>();
					list.add(vehicle);
					table.reload(list);
				}
			}
		}
	}
}

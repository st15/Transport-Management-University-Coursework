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
import org.springframework.dao.DataAccessException;

import com.trans.dao.DriverDao;
import com.trans.dao.VehicleModelDao;
import com.trans.domain.Driver;
import com.trans.domain.Vehicle;
import com.trans.ui.forms.DriverForm;
import com.trans.ui.tables.DriverTableModel;
import com.trans.ui.tables.TransportTable;
import com.trans.util.ImageUtil;

/**
 * 
 * @author Lili
 */
public class DriverTab extends JPanel {

	private static final long serialVersionUID = 1L;

	private static Logger LOG = LogManager.getLogger(DriverTab.class.getName());

	private DriverDao driverDao;
	private VehicleModelDao vehicleDao;

	private DriverForm form;
	private TransportTable<Driver> table;

	private JScrollPane tableScrollPane;

	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;

	private JButton btnSave;

	// filters
	private JTextField town;
	private JTextField vehicleCapacity;
	private JButton btnFilter;
	private JButton btnShowAll;

	public DriverTab(DriverDao driverDao, VehicleModelDao vehicleDao) {

		this.driverDao = driverDao;
		this.vehicleDao = vehicleDao;

		initializeComponents();
		addComponents();
		// load data
		refresh();
	}

	private void initializeComponents() {
		this.setLayout(new BorderLayout());

		// DriverForm
		form = new DriverForm();

//		refreshVehiclesList();

		Driver driver = new Driver();
		driver.setFirstName("insert new driver's name");
		form.setDriver(driver);

		// table
		table = new TransportTable<Driver>(new DriverTableModel());

		tableScrollPane = new JScrollPane();
		tableScrollPane.setPreferredSize(new Dimension(460, 210));
		tableScrollPane.setViewportView(table); // tableVehicles);
		tableScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		// buttons
		btnNew = new JButton("New", ImageUtil.createImageIcon("/res/add.png"));
		btnNew.addActionListener(new NewActionListener());

		btnEdit = new JButton("Edit", ImageUtil.createImageIcon("/res/edit.png"));
		btnEdit.addActionListener(new EditActionListener());

		btnDelete = new JButton("Delete", ImageUtil.createImageIcon("/res/remove.png"));
		btnDelete.addActionListener(new DeleteActionListener());

		btnSave = new JButton("Save", ImageUtil.createImageIcon("/res/ok.png"));
		btnSave.addActionListener(new SaveActionListener());

		// filters
		town = new JTextField();
		town.setColumns(15);

		vehicleCapacity = new JTextField();
		vehicleCapacity.setColumns(8);

		btnFilter = new JButton("Find", ImageUtil.createImageIcon("/res/find.png"));
		btnFilter.addActionListener(new FilterActionListener());
		
		btnShowAll = new JButton("Show All", ImageUtil.createImageIcon("/res/refresh.png"));
		btnShowAll.addActionListener(new ShowAllActionListener());
	}

	private void addComponents() {
		JPanel leftPanel = new JPanel(new BorderLayout());

		JPanel rightPanel = new JPanel(new BorderLayout());

		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Driver");
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

		rightPanel.add(form, BorderLayout.NORTH);
		rightPanel.add(rightPanelButtons, BorderLayout.SOUTH);

		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);

		// filters
		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JPanel townPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		townPanel.add(town);
		JLabel townLabel = new JLabel("Town", JLabel.RIGHT);
		townLabel.setLabelFor(town);

		JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		capacityPanel.add(vehicleCapacity);
		JLabel capacityLabel = new JLabel("Min vehicle capacity (kg)", JLabel.RIGHT);
		capacityLabel.setLabelFor(vehicleCapacity);

		filterPanel.add(townLabel);
		filterPanel.add(townPanel);
		filterPanel.add(capacityLabel);
		filterPanel.add(capacityPanel);
		filterPanel.add(btnFilter);
		filterPanel.add(btnShowAll);

		TitledBorder filterBorder = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Find drivers");
		filterBorder.setTitleJustification(TitledBorder.LEFT);

		filterPanel.setBorder(filterBorder);

		leftPanel.add(filterPanel, BorderLayout.NORTH);
	}

	private void refreshTable() {
		List<Driver> drivers = driverDao.findAll();
		table.reload(drivers);
	}
	
	private class ShowAllActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clearFilter();
			refreshTable();
		}
	}
	
	private class FilterActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedTown = town.getText();
			Integer selectedCapacity;
			try {
				selectedCapacity = Integer.parseInt(vehicleCapacity.getText()); 
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(DriverTab.this,
						"You have entered invalid data for vehicle capacity.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// find drivers by town
			List<Driver> driversByTownAndMinCapacity = driverDao.findAll(selectedTown, selectedCapacity);
			
			table.reload(driversByTownAndMinCapacity);
		}
	}
	
	private class NewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Driver driver = new Driver();
			driver.setFirstName("insert new driver's name");
			form.setDriver(driver);
		}
	}
	
	private class DeleteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Driver driverToDelete = table.getSelectedItem();
			driverDao.delete(driverToDelete.getId());
			refreshTable();
			JOptionPane.showMessageDialog(DriverTab.this, "Deleted driver "
					+ driverToDelete.toString() + ".", "Driver deleted",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class EditActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			form.setDriver(table.getSelectedItem());
		}
	}
	
	private class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Driver driver = form.getDriver();

			try {
				if (driver.getId() == 0) {
					driverDao.create(driver);

					JOptionPane.showMessageDialog(DriverTab.this,
							"Created driver " + driver.toString() + ".",
							"Driver created",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					driverDao.update(driver);
					JOptionPane.showMessageDialog(DriverTab.this,
							"Updated driver " + driver.toString() + ".",
							"Driver updated",
							JOptionPane.INFORMATION_MESSAGE);
				}
				clearFilter();
				refreshTable();
			} catch (DataAccessException dae) {
				LOG.error(dae.getMessage(), dae);

				JOptionPane.showMessageDialog(DriverTab.this,
						"You have entered invalid data.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void clearFilter() {
		town.setText("");
		vehicleCapacity.setText("0");
	}

	private void refreshVehiclesList() {
		
		List<Vehicle> vehicles = vehicleDao.findAll();
		List<Integer> vehicleIds = new ArrayList<Integer>();
		for (Vehicle v : vehicles) {
			vehicleIds.add(v.getVehicleNumber());
		}
		form.setVehiclesList(vehicleIds);
	}

	public void refresh() {
		clearFilter();
		refreshTable();
		refreshVehiclesList();
	}
}

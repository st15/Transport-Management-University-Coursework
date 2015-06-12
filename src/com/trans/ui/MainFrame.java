package com.trans.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.trans.dao.DriverDao;
import com.trans.dao.ModelDao;
import com.trans.dao.VehicleModelDao;
import com.trans.ui.tabs.DriverTab;
import com.trans.ui.tabs.VehicleTab;
import com.trans.util.ImageUtil;

/**
 * Main JFrame of the application.
 * 
 * @author Lili
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
//	private static Logger LOG = LogManager.getLogger(MainFrame.class.getName());
	
	private JTabbedPane tabbedPane;
	
	private DriverTab driversTab;
	private VehicleTab vehiclesTab;

	public MainFrame(DriverDao driverDao, VehicleModelDao vehicleDao, ModelDao modelDao) {

		setTitle("Транс'99");

		ImageIcon icon = ImageUtil.createImageIcon("/res/appicon.png");
		setIconImage(icon.getImage());
		
		// инициализация на компонентите
		driversTab = new DriverTab(driverDao, vehicleDao);
		vehiclesTab = new VehicleTab(driverDao, vehicleDao, modelDao);
		
		tabbedPane = new JTabbedPane();
		
		addComponents();

		pack();
		setLocationRelativeTo(null);
//		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void addComponents() {

		this.add(tabbedPane);
		
		tabbedPane.addTab("Drivers", null, driversTab,
                "Add and edit drivers");
		
		tabbedPane.addTab("Vehicles", null, vehiclesTab,
                "Add and edit vehicles");
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
	        public void stateChanged(ChangeEvent e) {
				// рефрешва се, защото номерата на превозните средства може да са променени в другия таб
	            if (tabbedPane.getSelectedComponent() == driversTab) {
	            	driversTab.refresh();
	            }
	        }
	    });
	}
}

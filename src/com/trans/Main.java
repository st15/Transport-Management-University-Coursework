package com.trans;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.trans.dao.DriverDao;
import com.trans.dao.ModelDao;
import com.trans.dao.VehicleModelDao;
import com.trans.ui.MainFrame;

/**
 * @author Lili
 *
 */
public class Main implements Runnable {
	
	private static Logger LOG = LogManager.getLogger(Main.class.getName());
	
	@Override
	public void run() {

	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(DatabaseConfiguration.getString("driver")); //$NON-NLS-1$
	    dataSource.setUrl(DatabaseConfiguration.getString("url")); //$NON-NLS-1$
	    dataSource.setUsername(DatabaseConfiguration.getString("username")); //$NON-NLS-1$
	    dataSource.setPassword(DatabaseConfiguration.getString("password")); //$NON-NLS-1$
	    
	    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		DriverDao driverDao = new DriverDao();
	    driverDao.setJdbcTemplate(jdbcTemplate);
	    
	    VehicleModelDao vehicleDao = new VehicleModelDao();
		vehicleDao.setJdbcTemplate(jdbcTemplate);
		
		ModelDao modelDao = new ModelDao();
		modelDao.setJdbcTemplate(jdbcTemplate);
		
		JFrame frame = new MainFrame(driverDao, vehicleDao, modelDao);
		
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				
				if ("Windows".equals(info.getName())) { //$NON-NLS-1$
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
		
		// Schedules the application to be run at the correct time in the event
		// queue.
		SwingUtilities.invokeLater(new Main());
	}
}
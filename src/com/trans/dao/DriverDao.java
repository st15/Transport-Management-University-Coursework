package com.trans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.trans.domain.Driver;

/**
 * @author Lili
 *
 */
public class DriverDao implements IDao<Driver> {
	
	private static Logger LOG = LogManager.getLogger(DriverDao.class.getName());

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplateObject) {
		this.jdbcTemplate = jdbcTemplateObject;
	}
	
	@Override
	public List<Driver> findAll() {
		String sql = "SELECT * FROM drivers ORDER BY id";
		List<Driver> drivers = jdbcTemplate.query(sql,
				new DriverMapper());
		return drivers;
	}
	
	public List<Driver> findAll(String town, Integer minVehicleCapacity) {
		String sql = "SELECT drivers.* FROM drivers, vehicles, models WHERE drivers.town LIKE '%' || ? || '%' AND drivers.vehicle_number = vehicles.vehicle_number AND vehicles.model_id = models.id AND models.load_capacity >= ?";
		List<Driver> drivers = jdbcTemplate.query(sql,
				new Object[] { town, minVehicleCapacity }, new DriverMapper());
		return drivers;
	}

	@Override
	public void create(Driver driver) {
		String sql = "INSERT INTO drivers (first_name, last_name, egn, vehicle_number, town, address) VALUES (?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, driver.getFirstName(),
				driver.getLastName(), driver.getEgn(), 
				driver.getVehicleNumber(), driver.getTown(),
				driver.getAddress());
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM drivers WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public void update(Driver driver) {
		String sql = "UPDATE drivers SET first_name = ?, last_name = ?, egn = ?, vehicle_number = ?, town = ?, address = ? where id = ?";
		jdbcTemplate.update(sql, driver.getFirstName(),
				driver.getLastName(), driver.getEgn(), 
				driver.getVehicleNumber(), driver.getTown(),
				driver.getAddress(),
				driver.getId());
		LOG.info("Updated Record with ID = " + driver.getId());
		return;
	}

	@Override
	public Driver find(Integer id) {
		String sql = "SELECT * FROM drivers WHERE id = ?";
		try {
			Driver driver = jdbcTemplate.queryForObject(sql,
					new Object[] { id }, new DriverMapper());
			return driver;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Driver find(String firstName, String lastName) {
		String sql = "SELECT * FROM drivers WHERE first_name = ? AND last_name = ?";
		try {
			Driver driver = jdbcTemplate.queryForObject(sql,
					new Object[] { firstName, lastName }, new DriverMapper());
			return driver;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	private class DriverMapper implements RowMapper<Driver> {
	
		@Override
		public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
			Driver driver = new Driver();
			
		      driver.setId(rs.getInt("id"));
		      driver.setFirstName(rs.getString("first_name"));
		      driver.setLastName(rs.getString("last_name"));
		      driver.setEgn(rs.getLong("egn"));
		      
		      int vehicleId = rs.getInt( "vehicle_number" );
		      driver.setVehicleNumber(rs.wasNull() ? null : vehicleId);

		      driver.setTown(rs.getString("town"));
		      driver.setAddress(rs.getString("address"));
		      return driver;
		}
	}
}


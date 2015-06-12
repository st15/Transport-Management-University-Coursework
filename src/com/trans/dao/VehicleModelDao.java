package com.trans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.trans.domain.Model;
import com.trans.domain.Vehicle;

/**
 * @author Lili
 *
 */
public class VehicleModelDao implements IDao<Vehicle> {
	
	private static Logger LOG = LogManager.getLogger(VehicleModelDao.class.getName());

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplateObject) {
		this.jdbcTemplate = jdbcTemplateObject;
	}
	
	@Override
	public List<Vehicle> findAll() {
		String sql = "SELECT vehicles.id, vehicle_number, registration_number, model_id, load_capacity, model FROM vehicles, models WHERE model_id = models.id";
		List<Vehicle> vehicles = jdbcTemplate.query(sql,
				new VehicleMapper());
		return vehicles;
	}

	@Override
	public void create(Vehicle vehicle) {
		String sql = "INSERT INTO vehicles (vehicle_number, registration_number, model_id) VALUES (?, ?, ?)";

		jdbcTemplate.update(sql, vehicle.getVehicleNumber(),
				vehicle.getRegistrationNumber(), vehicle.getModel().getId());
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM vehicles WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public void update(Vehicle vehicle) {
		String sql = "UPDATE vehicles SET registration_number = ?, model_id = ?, vehicle_number = ? where id = ?";
		jdbcTemplate.update(sql, 
				vehicle.getRegistrationNumber(),
				vehicle.getModel().getId(), 
				vehicle.getVehicleNumber(),
				vehicle.getId());
		LOG.info("Updated Record with ID = " + vehicle.getVehicleNumber());
		return;
	}

	@Override
	public Vehicle find(Integer id) {
		String sql = "SELECT vehicles.id, vehicle_number, registration_number, model_id, load_capacity, model FROM vehicles, models WHERE vehicles.id = ? AND model_id = models.id";
		try {
			Vehicle vehicle = jdbcTemplate.queryForObject(sql,
					new Object[] { id }, new VehicleMapper());
			return vehicle;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Vehicle findByNumber(Integer vehicleNumber) {
		String sql = "SELECT vehicles.id, vehicle_number, registration_number, model_id, load_capacity, model FROM vehicles, models WHERE vehicle_number = ? AND model_id = models.id";
		try {
			Vehicle vehicle = jdbcTemplate.queryForObject(sql,
					new Object[] { vehicleNumber }, new VehicleMapper());
			return vehicle;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	private class VehicleMapper implements RowMapper<Vehicle> {
	
		@Override
		public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			Model model = new Model();
			model.setId(rs.getInt("model_id"));
			model.setLoadCapacity(rs.getInt("load_capacity")); 
			model.setModel(rs.getString("model")); 
			
			Vehicle vehicle = new Vehicle();
			vehicle.setId(rs.getInt("vehicles.id"));
			vehicle.setVehicleNumber(rs.getInt("vehicle_number"));
			vehicle.setRegistrationNumber(rs.getString("registration_number"));
			vehicle.setModel(model);
			return vehicle;
		}
	}
}


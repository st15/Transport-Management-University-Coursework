package com.trans.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.trans.domain.Model;

/**
 * @author Lili
 *
 */
public class ModelDao implements IDao<Model> {
	
	private static Logger LOG = LogManager.getLogger(ModelDao.class.getName());

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplateObject) {
		this.jdbcTemplate = jdbcTemplateObject;
	}
	
	@Override
	public List<Model> findAll() {
		String sql = "SELECT * FROM models";
		List<Model> models = jdbcTemplate.query(sql,
				new ModelMapper());
		return models;
	}

	@Override
	public void create(Model model) {
		String sql = "INSERT INTO models (model, load_capacity) VALUES (?, ?)";

		jdbcTemplate.update(sql, 
				model.getModel(), model.getLoadCapacity());
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM models WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public void update(Model model) {
		String sql = "UPDATE models SET model = ?, load_capacity = ? where id = ?";
		jdbcTemplate.update(sql, 
				model.getModel(), model.getLoadCapacity(),
				model.getId());
		LOG.info("Updated Record with ID = " + model.getId());
		return;
	}

	@Override
	public Model find(Integer id) {
		String sql = "SELECT * FROM models WHERE id = ?";
		Model model = jdbcTemplate.queryForObject(sql,
				new Object[] { id }, new ModelMapper());
		return model;
	}

	private class ModelMapper implements RowMapper<Model> {
	
		@Override
		public Model mapRow(ResultSet rs, int rowNum) throws SQLException {
			Model model = new Model();
		      model.setId(rs.getInt("id"));
		      model.setModel(rs.getString("model"));
		      model.setLoadCapacity(rs.getInt("load_capacity"));
		      return model;
		}
	}
}


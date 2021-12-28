package org.example.app.services.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.*;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

	@Bean
	public DataSource getDataSource(){
		return new EmbeddedDatabaseBuilder()
			.generateUniqueName(false)
			.setName("book_store")
			.setType(EmbeddedDatabaseType.H2)
			.addDefaultScripts()
			.setScriptEncoding("UTF-8")
			.ignoreFailedDrops(true)
			.build();
	}

	@Bean
	public NamedParameterJdbcTemplate jdbcTemplate(){
		return new NamedParameterJdbcTemplate(getDataSource());
	}

}

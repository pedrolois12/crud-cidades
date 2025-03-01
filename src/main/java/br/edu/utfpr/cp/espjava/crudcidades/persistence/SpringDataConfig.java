package br.edu.utfpr.cp.espjava.crudcidades.persistence;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("br.edu.utfpr.cp.espjava.crudcidades")
@EnableTransactionManagement
public class SpringDataConfig {

    @Bean
    public DataSource dataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setJdbcUrl("jdbc:mariadb://localhost:3306/javaweb");
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        return ds;
    }

    

}

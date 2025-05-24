package com.projeto.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;





    //IMPLEMENTAÇÃO BÁSICA DO DATASOURCE - NÃO RECOMENDADO PARA USO EM PRODUÇÃO!
//  @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }



    // IMPLEMENTAÇÃO HIKARI -> RECOMENDADO -> POR PADRÃO, JA  VEM NO SPRING
    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        //LIMITA A QUANTIDADE DE CONEXÕES DE ACESSO AO BANCO
        config.setMaximumPoolSize(10);
        // CONFIGURA TAMANHO INICIAL DO POOL
        config.setMinimumIdle(1);
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600 mil ms -> (10 minutos) -> Maximo de tempo de conexão
        config.setConnectionTimeout(100000); // TIMEOUT PARA CONSEGUIR UMA CONEXÃO
        config.setConnectionTestQuery("select 1"); // QUERY DE TESTE


        return new HikariDataSource(config);

    }



}

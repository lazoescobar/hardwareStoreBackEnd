package com.hardwareStore.backEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
//@ComponentScan(basePackages = {"com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios"})
//@EnableJpaRepositories(basePackages = {"com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.*"})
//@EnableJpaRepositories(basePackages = {"com.hardwareStore.backEnd.moduloUsuarios.persistencias.repositorios",
										//"com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios"},
		//enableDefaultTransactions = false)
@SpringBootApplication
public class BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

}

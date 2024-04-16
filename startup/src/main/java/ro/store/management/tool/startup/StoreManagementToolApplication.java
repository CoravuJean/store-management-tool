package ro.store.management.tool.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;
import ro.store.management.tool.config.WebSecurityConfig;
import ro.store.management.tool.controller.StoreController;
import ro.store.management.tool.database.config.DatabaseConfiguration;
import ro.store.management.tool.exception.GlobalExceptionHandler;
import ro.store.management.tool.exception.UncaughtExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Import({StoreController.class, GlobalExceptionHandler.class})
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
public class StoreManagementToolApplication {

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

		SpringApplication springApplication = new SpringApplication(StoreManagementToolApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.SERVLET);
		Set<Class<?>> additionalPrimarySources = new HashSet<>();
		additionalPrimarySources.add(DatabaseConfiguration.class);
		additionalPrimarySources.add(WebSecurityConfig.class);
		springApplication.addPrimarySources(additionalPrimarySources);
		springApplication.run(args);
	}
}

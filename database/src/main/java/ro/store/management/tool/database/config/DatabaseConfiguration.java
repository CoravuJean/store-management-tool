package ro.store.management.tool.database.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ro.store.management.tool.database.dao.ProductDao;

import javax.sql.DataSource;

@Configuration
@Import({ProductDao.class})
@Slf4j
public class DatabaseConfiguration {

    public DatabaseConfiguration() {
    }

    @Bean
    public DataSource dataSource() {
        if (log.isInfoEnabled()) {
            log.info("Using in memory database.");
        }

        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:schema-database.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

package dictionarySpring.configuration.jdbcConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("dictionarySpring")
@PropertySource(value = "classpath:hibernate.properties")
@EnableTransactionManagement
public class JdbcConfiguration {

    private final DataSource dataSource;

    public JdbcConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource);
    }
}

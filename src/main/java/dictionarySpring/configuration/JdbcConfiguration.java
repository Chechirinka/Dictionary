package dictionarySpring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("dictionarySpring")
@PropertySource(value = "classpath:hibernate.properties")
@EnableTransactionManagement
public class JdbcConfiguration {

    private final Environment env;

    public JdbcConfiguration(Environment env) {
        this.env = env;
    }

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

            dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driver.class")));
            dataSource.setUrl(env.getProperty("connection.url"));
            dataSource.setUsername(env.getProperty("connection.username"));
            dataSource.setPassword(env.getProperty("connection.password"));

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
    }
}

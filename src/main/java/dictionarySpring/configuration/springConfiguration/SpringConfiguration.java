package dictionarySpring.configuration.springConfiguration;

import dictionarySpring.dao.DictionaryCriteria;
import dictionarySpring.dao.DictionaryDAO;
import dictionarySpring.dao.DictionaryJpaHql;
import dictionarySpring.dao.DictionaryStorage;
import dictionarySpring.dao.FileStorage;
import dictionarySpring.dao.MapStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("dictionarySpring")
@PropertySource(value = "classpath:properties.yml")
@PropertySource(value = "classpath:hibernate.properties")
@EnableTransactionManagement
@EnableWebMvc
@Import({
        org.springdoc.webmvc.ui.SwaggerConfig.class,
        org.springdoc.core.SwaggerUiConfigProperties.class, org.springdoc.core.SwaggerUiOAuthProperties.class,
        org.springdoc.webmvc.core.SpringDocWebMvcConfiguration.class,
        org.springdoc.webmvc.core.MultipleOpenApiSupportConfiguration.class,
        org.springdoc.core.SpringDocConfiguration.class, org.springdoc.core.SpringDocConfigProperties.class
})

public class SpringConfiguration implements WebMvcConfigurer {

    private static final String MAP = "map";
    private static final String FILE = "file";
    private static final String JDBC = "jdbc";
    private static final String HQL = "hql";
    private static final String CRITERIA = "criteria";
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfiguration(ApplicationContext applicationContext, Environment env) {
        this.applicationContext = applicationContext;
    }

    @Bean(name = "dictionaryFactory")
    public DictionaryStorage getDictionary(@Value("${type}") String args) {
        switch (args) {
            case (MAP):
                return new MapStorage();
            case (FILE):
                return new FileStorage();
            case (JDBC):
                return new DictionaryDAO();
            case (HQL):
                return new DictionaryJpaHql();
            case (CRITERIA):
                return new DictionaryCriteria();
        }
        return new FileStorage();
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }
}


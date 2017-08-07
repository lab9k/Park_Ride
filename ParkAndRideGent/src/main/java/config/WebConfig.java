package config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import repository.InMemoryRepository;
import repository.Repository;

@Configuration
@EnableWebMvc
@ComponentScan({"controller","api"})
@PropertySource("classpath:config.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    private Repository repository;
    private GeoApiContext context;

    @Bean
    public Repository repository() {
        if(repository == null)
            repository = new InMemoryRepository();
        return repository;
    }

    @Bean
    public GeoApiContext geoApiContext() {
        if(context == null)
            context = new GeoApiContext().setApiKey(env.getProperty("GMAPS.KEY"));
        return context;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("resources/");
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

}

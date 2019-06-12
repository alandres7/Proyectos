package co.test.ada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@EntityScan(basePackages = "co.test.ada.entity")
@ComponentScan(basePackages = {
		"co.test.ada.entity",
		"co.test.ada.gateway",
		"co.test.ada.repo",
		"co.test.ada.svc",
		"co.test.ada.rest"
})
@EnableJpaRepositories(basePackages = "co.test.ada.repo")
@EnableAutoConfiguration
@SpringBootApplication
public class PruebaPolygonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaPolygonApplication.class, args);
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
	    return new HibernateJpaSessionFactoryBean();
	}

}

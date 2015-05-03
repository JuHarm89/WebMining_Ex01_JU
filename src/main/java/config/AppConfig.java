package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import service.StopWordsService;
import service.StopWordsServiceImpl;
import service.WordExtractorService;
import service.WordExtractorServiceImpl;
import service.WordExtractorServiceMockImpl;


@Configuration
@ComponentScan(basePackages = "service")
@Import(value = ControllerConfig.class )
@PropertySource("classpath:application.properties")
public class AppConfig {
	
	@Bean
	public StopWordsService stopWordService(){
		return new StopWordsServiceImpl();
	}
	
	@Bean
	public WordExtractorService wordExtractorService(){
		return new WordExtractorServiceImpl();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}

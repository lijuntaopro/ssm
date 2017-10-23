package cn.lijuntao.ssm.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
//配置标签，专门配置一些bean
@Configuration
@ComponentScan(basePackages = "cn.lijuntao.ssm")
//加载其他配置
@Import(SecondConfig.class)
@EnableAspectJAutoProxy
//@PropertySource("classpath:log4j.properties")
public class FirstConfig {
	@Value("log4j.rootCategory")
	private String rootCategory;
	
	@Bean
	public DateFormat getDateFormat(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	
}

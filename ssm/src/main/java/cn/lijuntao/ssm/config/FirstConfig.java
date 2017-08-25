package cn.lijuntao.ssm.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
//���ñ�ǩ��ר������һЩbean
@Configuration
@ComponentScan(basePackages = "cn.lijuntao.ssm")
//������������
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

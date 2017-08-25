package cn.lijuntao.ssm.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import cn.lijuntao.ssm.interceptor.TimeBasedAccessInterceptor;

//配置标签，专门配置一些bean
@Configuration
@ComponentScan("cn.lijuntao.ssm")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Bean
	public FormattingConversionService conversionService() {

		// Use the DefaultFormattingConversionService but do not register
		// defaults
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

		// Ensure @NumberFormat is still supported
		conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());

		// Register date conversion with a specific global format
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("yyyyMMdd"));
		registrar.registerFormatters(conversionService);

		return conversionService;
	}
	
	@Bean
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter(){
    	RequestMappingHandlerAdapter mappingHandlerAdapter = new RequestMappingHandlerAdapter();
    	StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
    	MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
    	CastorMarshaller marshaller = new CastorMarshaller();
    	marshallingHttpMessageConverter.setMarshaller(marshaller);
    	marshallingHttpMessageConverter.setUnmarshaller(marshaller);
    	List<HttpMessageConverter<?>> arrayList = new ArrayList<HttpMessageConverter<?>>();
    	arrayList.add(stringHttpMessageConverter);
    	arrayList.add(marshallingHttpMessageConverter);
    	mappingHandlerAdapter.setMessageConverters(arrayList);
    	return mappingHandlerAdapter;
    }
    
    @Bean
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping(){
    	RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
    	TimeBasedAccessInterceptor interceptor = new TimeBasedAccessInterceptor();
    	requestMappingHandlerMapping.setInterceptors(interceptor);
    	return requestMappingHandlerMapping;
    }
    
    @Bean
    public ViewResolver getViewResolver(){
    	InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    	viewResolver.setPrefix("/WEB-INF/jsp/");
    	viewResolver.setSuffix(".jsp");
    	viewResolver.setExposeContextBeansAsAttributes(true);
    	return viewResolver;
    }
    
    //@Bean
    public ViewResolver getViewResolver2(){
    	UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
    	viewResolver.setPrefix("/WEB-INF/jsp/");
    	//需要有jstl包
    	viewResolver.setViewClass(JstlView.class);
    	viewResolver.setSuffix(".jsp");
    	viewResolver.setExposeContextBeansAsAttributes(true);
    	return viewResolver;
    }
    
    //@Bean
    public MultipartResolver getCommonsMultipartResolver(){
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    	multipartResolver.setMaxUploadSize(1024*1024*20);
    	return multipartResolver;
    }
    
    /**
     * servlet 3.0 自带属性
     * @author 李俊涛
     * @date 2017年8月25日
     * @return
     */
    @Bean
    public MultipartResolver getStandardServletMultipartResolver(){
    	StandardServletMultipartResolver standardServletMultipartResolver = new StandardServletMultipartResolver();
    	return standardServletMultipartResolver;
    }
    

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/*.html").addResourceLocations("/");
    	registry.addResourceHandler("/*.css").addResourceLocations("/");
    	registry.addResourceHandler("/*.js").addResourceLocations("/");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public-resources/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }

}

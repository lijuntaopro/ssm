package cn.lijuntao.ssm.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.lijuntao.ssm.config.FirstConfig;
import cn.lijuntao.ssm.config.SecondConfig;
import cn.lijuntao.ssm.config.service.HelloService;
@Controller
@RequestMapping("/app/")
public class HelloController {
	@Autowired
	private HelloService helloService;
	
	@RequestMapping(value = "say", method = RequestMethod.GET )
	public String say(){
		System.out.println(helloService.say());
		return "say";
	}
	
	@RequestMapping(value = "error", method = RequestMethod.GET )
	public String error() throws IOException{
		throw new IOException("kkas");
	}
	
	public static void main(String[] args) {
		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FirstConfig.class);
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(FirstConfig.class);
		ctx.register(SecondConfig.class);
		ctx.refresh();
		DateFormat bean = ctx.getBean(DateFormat.class);
		HelloController bean2 = ctx.getBean(HelloController.class);
		HelloService service = ctx.getBean(HelloService.class);
		System.out.println(bean.format(new Date()));
		System.out.println(bean2);
		System.out.println(service.say());
	}
	
	public int[] get(){
		ExpressionParser parser = new SpelExpressionParser();
		int[] numbers2 = (int[]) parser.parseExpression("new int[]{1,2,3}").getValue();
		return numbers2;
	}
	
	@Test
	public void test(){
		System.out.println(Arrays.toString(this.get()));
	}
}

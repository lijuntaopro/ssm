package cn.lijuntao.ssm.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class HelloAop {
	@Before("cn.lijuntao.ssm.config.service.HelloService.*(..) && args(account,..)")
    public void doAccessCheck() {
        // ...
    }
	
	@AfterThrowing(pointcut="cn.lijuntao.ssm.config.service.HelloService.*(..)",throwing="ex")
    public void doRecoveryActions(Exception ex) {
        // ...
    }
	
	@Around("cn.lijuntao.ssm.config.service.HelloService.*(..)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
	
	@Before("execution(* ..Sample+.sampleGenericMethod(*)) && args(param)")
	public void beforeSampleMethod(String param) {
	    // Advice implementation
	}
	
	@Before(value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)",
	        argNames="bean,auditable")
	public void audit(Object bean, String auditable) {
	    // ... use code and bean
	}
	
	@Before(value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)",
	        argNames="bean,auditable")
	public void audit(JoinPoint jp, Object bean, String auditable) {
	    // ... use code, bean, and jp
	}
}

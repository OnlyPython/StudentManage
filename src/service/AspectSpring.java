package service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectSpring {
	
//	private static Logger logger = LoggerFactory.getLogger(AspectSpring.class);
	
	private static final ThreadLocal<Long> START_TIME_THREADLOCAL = new ThreadLocal<>(); 
	
	@Pointcut("execution(* dao..*.*Dao*.search*(..))")
	public void pointcut(){}
	
	@Before(value="pointcut()")
	public void beforeAdvise(JoinPoint jp){
		System.out.println("Method Name: " + jp.getSignature().getName());
//		logger.info("Method Name: " + jp.getSignature().getName());
		START_TIME_THREADLOCAL.set(System.nanoTime());
	}
	
	@After(value="pointcut()")
	public void afterAdvise(JoinPoint jp){
		Long startTime = START_TIME_THREADLOCAL.get();
		long endTime = System.nanoTime();
//		logger.info("Run Time = " + (endTime - startTime));
		System.out.println("Run Time = " + (endTime - startTime));
	}
	/**
	 * 
	 * @param ex
	@AfterThrowing(value="execution(* service.*Service.save*(..)) ||"
			+ "execution(* service.*Service.*update*(..)) ||"
			+ "execution(* service.*Service.delete*(..))", throwing = "ex")
	public void throwException(RuntimeException ex){
	}
	
	@Around(value="execution(* service.*Service.save*(..)) ||"
			+ "execution(* service.*Service.*update*(..)) ||"
			+ "execution(* service.*Service.delete*(..))")
	public Object around(ProceedingJoinPoint jpt) throws Throwable{
		Object ob = jpt.proceed();
		return ob;
	}
	 */
	
}

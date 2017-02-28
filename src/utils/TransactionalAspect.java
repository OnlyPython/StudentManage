package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionalAspect {
	private static final ThreadLocal<Connection> CONNECTION_THREADLOCAL = new ThreadLocal<Connection>();
	private static final ThreadLocal<Savepoint> SAVEPOINT_THREADLOCAL = new ThreadLocal<Savepoint>();
	
	@Resource
	private DataSource dataSource;
	
	
	public Connection getConnection() throws SQLException{
		Connection connection = CONNECTION_THREADLOCAL.get();
		if(connection == null){
			System.out.println("========= Get Connection ========");
			connection = dataSource.getConnection();
			CONNECTION_THREADLOCAL.set(connection);
		}
		return connection;
	}
	// 给Service类中的方法定义切面，因为一个Service方法可能调用多个Dao方法，使用一个事务即可。
	@Pointcut(value="execution(* service.*Service.*save*(..)) || "
			+ "execution(* service.*Service.*update*(..)) || "
			+ "execution(* service.*Service.*delete*(..))")
	public void txPointCut(){}
	
	@Before(value="txPointCut()")
	public void before() throws SQLException{
		Connection connection = getConnection();
		Savepoint savepoint = SAVEPOINT_THREADLOCAL.get();
		if(savepoint==null){
			System.out.println("========= Open Transaction ========");
			connection.setAutoCommit(false);
			connection.setSavepoint();
			SAVEPOINT_THREADLOCAL.set(savepoint);
		}
	}
	
	@AfterReturning(value="txPointCut()")
	public void afterReturning() throws SQLException{
		Connection connection = CONNECTION_THREADLOCAL.get();
		System.out.println("========= Commit Transaction ========");
		connection.commit();
		connection.close();
		CONNECTION_THREADLOCAL.remove();
		SAVEPOINT_THREADLOCAL.remove();
	}
	
	@AfterThrowing(value="txPointCut()", throwing="ex")
	public void afterThrowing(RuntimeException ex) throws SQLException{
		Connection connection = CONNECTION_THREADLOCAL.get();
		Savepoint savepoint = SAVEPOINT_THREADLOCAL.get();
		System.out.println("========= Exception Message: " + ex.getMessage() + " Start Tp Rollback =======");
		connection.rollback(savepoint);
		connection.close();
		CONNECTION_THREADLOCAL.remove();
		SAVEPOINT_THREADLOCAL.remove();
	}
	
	/**
	 * 无事务时的切片
	 * @throws SQLException 
	 *
	 *execution(* service.*Service.*(..)) && not txPointCut() Spring的bug，not前必须有表达式且必须用&&连接
	 */
	@Before(value="execution(* service.*Service.*find*(..)) || "
			+ "execution(* service.*Service.*search*(..))")
	public void beforeNoTX() throws SQLException{
		System.out.println("======== Get Connection Without Open TX ========");
		getConnection();
	}
	
	@After(value="execution(* service.*Service.*find*(..)) || "
			+ "execution(* service.*Service.*search*(..))")
	public void afterNoTX() throws SQLException{
		System.out.println("======== Close Connection Without Open TX ========");
		Connection connection = CONNECTION_THREADLOCAL.get();
		connection.close();
		CONNECTION_THREADLOCAL.remove();
	}
}

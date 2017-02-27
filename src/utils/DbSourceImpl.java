package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Component;
@Component("dbSource")
public class DbSourceImpl implements DbSource {
	
	static Properties pro = new Properties();

	static{
		try (InputStream input = DbSourceImpl.class.getResourceAsStream("/common.properties")) {
			pro.load(input);
			//1. 加载数据库驱动
			Class.forName(pro.getProperty("db.driver"));
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public Connection getConnection(){
		Connection con=null;
		try {
			//2. 取得Connection
			con = DriverManager.getConnection(pro.getProperty("db.url"),pro.getProperty("db.username"),pro.getProperty("db.password"));
		} catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}

}

package 图书管理系统2;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCUtil {
	private static Properties ps = new Properties();
	//static 只一次 读取 解析
	static {
		//读取
		InputStream is = null;
		try {
			is = JDBCUtil.class.getResourceAsStream("/conf/jdbc.properties");
			ps.load(is);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError();
		}finally {
			if(is!=null) try {is.close();}catch(Exception e) {}
		}
	}
	//使用
	//方法名: 方法的返回值Connection 所以方法名: getConnection
	public static Connection getConnection() throws Exception{
		//使用
		Class.forName(ps.getProperty("driver"));
		String url = ps.getProperty("url");
		String user = ps.getProperty("username");
		String password = ps.getProperty("password");
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
}

package 图书管理系统2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MariaTools {

	///// * jdbcStart() */////
	///// * 获取jdbc连接工具
	public static Connection jdbcStart(){
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	///// * 对半成品的sql进行填全工具 */////
	public static PreparedStatement getFullPreparedStatementPS(String sql, ArrayList<Object> userDatas) {
		// 传入一个setObject的需求的总数量
		PreparedStatement ps = null;
		try {
			ps = MariaTools.jdbcStart().prepareStatement(sql);
			for (int i = 0; i < userDatas.size(); i++) {
				ps.setObject(i + 1, userDatas.get(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	public static PreparedStatement getFullPreparedStatementPS(String sql, Object element) {
		// 传入一个setObject的需求的总数量
		PreparedStatement ps = null;
		try {
			ps = MariaTools.jdbcStart().prepareStatement(sql);
			ps.setObject(1, element);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	///// * 关流工具 */////
	public static void close(ResultSet rs, Statement ps, Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (Exception e) {
			}
		if (ps != null)
			try {
				ps.close();
			} catch (Exception e) {
			}
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
	}

}

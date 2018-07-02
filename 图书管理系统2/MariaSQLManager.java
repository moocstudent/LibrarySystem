package 图书管理系统2;

/**
 * 针对所有表可查方法.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * 创建时间：2017年12月4日 上午12:37:45 项目名称：网络编程
 * 
 * @author ukyozq
 * @version 1.0
 * @since JDK 9.0
 * 
 */
public class MariaSQLManager {
	private static Scanner in = new Scanner(System.in);
	static String sql1;
	// public static void main(String[] args) {

	// System.out.println("输入您想查询的表的名称: XE Oracle <<<<");
	//
	// String tableName = in.next();

	/////*   sql_Handler的重载形式     */////
	// 设置sql_Handler的重载形式 多个值的设置
	protected static void sql_Handler(String half_SQL, ArrayList<Object> userDatas) {
		// Connection conn = MariaTools.jdbcStart();
		// PreparedStatement ps = conn.prepareStatement(half_SQL);
		PreparedStatement ps = MariaTools.getFullPreparedStatementPS(half_SQL, userDatas);
		try {
			for(int i=0;i<userDatas.size();i++) {
				ps.setObject(i+1, userDatas.get(i));
			}
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 设置sql_Handler的重载形式, 单值
	public static void sql_Handler(String card_id_SQL, Object element) {

		PreparedStatement ps = MariaTools.getFullPreparedStatementPS(card_id_SQL, element);
		try {
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 假设可以接受另一个类穿过来的SQL语句,则不需要进行提示等信息. 针对图书管理系统选项6操作(即通用SQL处理器)

	protected static void sql_Handler(String input_SQL) {
		sql1 = input_SQL;
		sql1.toLowerCase();
		int sql1Len = sql1.length();
		/* 如果输入的sql有分号结尾;则去掉 */
		if (sql1.endsWith(";")) {
			sql1 = sql1.substring(0, sql1Len - 1);
		}
		String[] oneOfSql1Array = sql1.split(" ");
		String getTableName1 = null;

		// 是选择,插入还是更新语句?当判定到为哪种sql后,给予true,并执行对应操作.//
		boolean isSelectSQL = false;
		boolean isInsertSQL = false;
		boolean isUpdateSQL = false;
		boolean isDeleteSQL = false;
		//////////////////////
		if (oneOfSql1Array[0].equals("select")) {
			isSelectSQL = true;
		} else if (oneOfSql1Array[0].equals("insert")) {
			isInsertSQL = true;
		} else if (oneOfSql1Array[0].equals("update")) {
			isUpdateSQL = true;
		} else if (oneOfSql1Array[0].equals("delete")) {
			isDeleteSQL = true;
		}

		for (int i = 0; i < oneOfSql1Array.length; i++) {
			if (oneOfSql1Array[i].equals("from")) {
				getTableName1 = oneOfSql1Array[i + 1];
				break;
			}
		}

		//////////////////////////////////////////////////////////////////////////////////////

		///// *以下方法用于将原本输出到控制台的内容写入到了文件里*/////
		///// System.setOut() /////
		////////////////////////////////////////////////////////////////////////////////
		// if (isSelectSQL) {
		// File file = new File(getTableName1 + "_sql_info.txt");
		// System.out.println("您好,您所查询的数据库信息将写入到" + file.getName() + "中.💗");
		// try {
		// file.createNewFile();
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// // file为根据输入查询的tableName组成的txt文件实例.
		// FileOutputStream fops = null;
		// try {
		// fops = new FileOutputStream(file);
		// } catch (FileNotFoundException e1) {
		// e1.printStackTrace();
		// }
		// PrintStream psm = new PrintStream(fops);
		// // 将该方法下面的要System.out.print() println() 输出的内容不再用于控制台输出,直接写入文件.
		// System.setOut(psm);
		// }

		////////////////////////////////////////////////////////////////////////////////

		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ResultSet rset = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// 注册JDBC驱动. {Oracle的OracleDriver}
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 测试连接时间
			long start = System.currentTimeMillis();
			/*
			 * DriverManager 接口是JDBC的管理层,作用于用户和驱动程序之间, DriverManager
			 * 跟踪可用的驱动程序,并在数据库和相应的驱动程序之间建立连接.
			 */
			// 建立连接(连接对象内部其实包含了Socket对象,是一个远程的连接,比较耗时!这是Connection对象管理的一个要点!)
			conn = MariaTools.jdbcStart();
			// 测试连接时间
			long end = System.currentTimeMillis();
			// System.out.println(conn);
			long useTime = end - start;
			System.out.println("💗与数据库建立连接成功💗,耗时:" + useTime + "毫秒.");
			System.out.println("------");
			// 创建一个会话

			// 使用PreparedStatement (效率更高,预处理机制.防止SQL注入) //占位符?
			// String sql = "SELECT employee_id FROM " + tableName;

			///// * 原本使用齐全的sql查询不会受影响 */////
			///// * 而使用半成品sql,以及数据集合传入的话(其他类中传入),会根据这个传入的ps来执行操作
			String sql = sql1;
			ps = conn.prepareStatement(sql);

			/////
			long startQuery = System.currentTimeMillis();

			if (isSelectSQL) {
				// 执行 用结果集获取
				rs = ps.executeQuery(sql);
				rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				// 输出列名
				for (int i = 1; i <= columnCount; i++) {
					System.out.print(rsmd.getColumnName(i) + ":\t");
				}
				System.out.println();
				//

				RegisterStuff reg = new RegisterStuff();
				LoginStuff log = new LoginStuff();
				reg.setNameCheckNum(0);// 设置用户名查询不重复0. 接下来如果重复就会被查询到在while中设置了1
				log.setID_IsPassed(false);// 设置了ID不存在,如果接下来在while中查询到的数据不为空则证明查询到了该ID,并传入true(表示存在)
				while (rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						System.out.print(rsmd.getColumnName(i) + ":\t");
						Object data = rs.getObject(i);
						System.out.print(data + "\t");
						///// * 只针对user_name判定的集合使用 */////
						if (oneOfSql1Array[1].equals("user_name")) {
							reg.setNameCheckNum(1);
						}
						if (oneOfSql1Array[1].equals("card_id")) {
							reg.setCardId(Long.parseLong(data.toString()));
						}
						// 如果数据不为空,在执行 select * from lib_users where card_id = 输入的ID,
						// 则查看该数据如果不为空,则传入true,即查询的输入的ID是存在的.
						if (data != null) {
							log.setID_IsPassed(true);
						}
						// 数据类型,在查看书时先隐藏
						// System.out.print(
						// "(数据类型:" + rsmd.getColumnTypeName(i) + "," + rsmd.getColumnDisplaySize(i) +
						// "字节)\t");
					}
					System.out.println();
				}
				long endQuery = System.currentTimeMillis();
				long useTimeQuery = endQuery - startQuery;
				System.out.println("查询成功!哈嘿😄💗,耗时:" + useTimeQuery + "毫秒.");

				System.out.println("------");
				// 取得列数(字段数)
				rset = ps.executeQuery("select count(*) totalCount from " + getTableName1);
				int rowCount = 0;
				while (rset.next()) {
					rowCount = rset.getInt("totalCount");

				}
				System.out.println(rsmd.getCatalogName(rowCount) + "数据库中的" + rsmd.getTableName(rowCount) + "表,有"
						+ rowCount + "行" + columnCount + "列.");

				System.out.println("------");
				System.out.println("执行了select语句.💗");
				// 执行完后继续执行MariaSQLManager
				// sql_Handler();
			} else if (isInsertSQL) {
				ps = conn.prepareStatement(sql);
				ps.execute();
				// 执行完后继续执行MariaSQLManager
				// sql_Handler();
				System.out.println("执行了insert语句.💗");
			} else if (isUpdateSQL) {
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				System.out.println("执行了update语句.💗");
				// 执行完后继续执行MariaSQLManager
				// sql_Handler();
			} else if (isDeleteSQL) {
				ps = conn.prepareStatement(sql);
				ps.execute();
				// 执行完后继续执行MariaSQLManager
				// sql_Handler();
				System.out.println("执行了delete语句.💗");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rsmd != null) {
					rset.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// sql_Handle end

	///// * sql管理器的提示器 */////
	protected static void sql_Helper() {

		///////////////////////////////////////////////////////////////////////////////////////
		System.out.println("输入您的SQL语句:只针对Oracle-XE <<<,输入'#'返回图书馆欢迎页.💗");
		System.out.println("查询数据格式:");
		System.out.println("select 字段 from 表名 (其他约束条件)");
		System.out.println("插入数据格式:");
		System.out.println("insert into 表名 values(seq_books.nextval,书名,作者,出版社,0,类别,价格)");
		System.out.println("更改数据模式:");
		System.out.println("update 表名 set 字段名=值 where book_id = 多少");
		System.out.println("删除数据模式:");
		System.out.println("delete from 表名 where 字段 = 数值");
		sql1 = in.nextLine();
		// 如果接受到#则返回到书城的欢迎页
		sql1 = sql1.trim();
		if (sql1.equals("#") && sql1.length() == 1) {
			LibrarianService.getHello();
			return;
		}
		sql_Handler(sql1);
		// 所有连接在获取后必须关闭. 后开的先关
		// 关闭顺序:ResultSet-->Statement(PreparedStatement)-->Connection

	}

}
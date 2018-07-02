package 图书管理系统2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) {

		try {
			// 读取
			InputStream is = TestProperties.class.getResourceAsStream("/conf/jdbc.properties");

			// 解析 保存
			Properties ps = new Properties();

			ps.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

package 图书管理系统2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UserDatabase {

	private static Set<String> userNameList = new HashSet<String>();
	private static Set<User> userInstance = new HashSet<User>();
	private static HashMap<Long, User> UserData = new HashMap<Long, User>();

	public static void setUserNameList(String userName) {
		userNameList.add(userName);
	}

	public static void setUserInstance(User newUser) {
		UserDatabase.userInstance.add(newUser);
	}

	public static void setUserData(HashMap<Long, User> userData) {
		UserDatabase.UserData = userData;
	}

	public static Set<String> getUserNameList() {
		return userNameList;
	}

	public static Set<User> getUserInstance() {
		return userInstance;
	}

	public static HashMap<Long, User> getUserDataMap() {
		return UserData;
	}

	// 错误,每次数据库初始化的话admin都带有一个随机ID BUG 待修复
	public static void UserDatabaseInit() {
		// 用户名不用于展示,昵称可以.这里昵称是 管理员
		User user1 = new User("admin", "admin", "男", "管理员", 22);
		setUserInstance(user1);
	}

	// 测试
	// public static void main(String[] args) {
	//// UserDatabaseInit();
	// ArrayList<User> users = getUserList();
	// for (User u : users) {
	// System.out.println(u);
	// }
	// }

}

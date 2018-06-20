package 图书管理系统2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterStuff {

	static Scanner in = new Scanner(System.in);
	private static String userName;
	private static String passWord;
	private static String sex;
	private static String nickName;
	private static int age;

	public User newRegisterStuff(String username, String password1, String password2, String sex, String nickname,
			int age) {

		String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(username);
		boolean b = match.matches();
		int alreadyHasUserName = 1;

		boolean userNamePassed = (username != "" || username != null || username.length() != 0);
		if (userNamePassed) {
			if (!b) {
				System.out.println("用户名只能由数字字母和下划线组成!请重新输入!💗");
				register();
				// return;
			} else {
				// 用户名格式通过后,再判定用户名是否存在于已有用户的用户名字段中,已存在则返回true,不存在返回false
				alreadyHasUserName = userNameAgain(username);
				if (alreadyHasUserName == 1) {// 已经存在该账户名,则重新输入
					// 去判定用户名有一个什么标记才行呢?使用boolean和int去判定最终还是会不行.
					System.out.println("该用户名已经存在,请选用其他用户名~💗");
					username = null;
					register();

					// return;
				} else if (alreadyHasUserName == 0) {// 不重复则通过,并赋值给this.userName
					this.userName = username;
				}
			}
		} else {
			while (!userNamePassed) {
				System.out.println("用户名为空,请重新填写注册信息.💗");
				register();
				// return;
			}
		}
		// 判定密码
		boolean passwordPassed = password1.equals(password2);
		if (passwordPassed) {
			this.passWord = password1;
		} else {
			while (!passwordPassed) {
				System.out.println("密码两次不一致,请核对后再次输入!(只输入两次密码💗)");
				String password_ = in.next();
				String password2_ = in.next();
				passwordPassed = passWordAgain(password_, password2_);
				if (passwordPassed) {
					this.passWord = password_;
				}
			}
		}

		// 判定性别输入 (不足)英文只支持小写.
		boolean sexPassed = (sex.equals("男") || sex.equals("女") || sex.equals("man") || sex.equals("man")
				|| sex.equals("woman") || sex.equals("boy") || sex.equals("girl"));
		if (sexPassed) {
			this.sex = sex;
		} else {
			while (!sexPassed) {
				System.out.println("性别填写错误,请再次填写:");
				String sex_ = in.next();
				sexPassed = sexAgain(sex_);
				if (sexPassed) {
					this.sex = sex_;
				}
			}
		}
		// 判定昵称合法性
		boolean nickNamePassed = (nickname != "" || nickname != null || nickname.length() != 0);
		if (nickNamePassed) {
			this.nickName = nickname;
		} else {
			while (!nickNamePassed) {
				System.out.println("昵称为空,请再次填写:");
				String nickname_ = in.next();
				nickNamePassed = nickNameAgain(nickname_);
				if (nickNamePassed) {
					this.nickName = nickname_;
				}
			}
		}
		// 判断年龄合法性
		boolean agePassed = (age < 999 && age > 0);
		if (agePassed) {
			this.age = age;
		} else {
			while (!agePassed) {
				System.out.println("只接受0岁以上儿童以及999岁以内的银河系生物!请再次输入年龄!");
				int age_ = in.nextInt();
				agePassed = ageAgain(age_);
				if (agePassed) {
					this.age = age_;
				}
			}
		}
		// 结束了所有数据的判断
		/////////////////////////////////////////////////////////////////////////////////
		boolean AuthPassed = alreadyHasUserName == 0 && userNamePassed && b && passwordPassed && sexPassed
				&& nickNamePassed && agePassed;
		User newUser = null;
		// 如果注册成功则创建新user对象.
		if (AuthPassed) {
			newUser = new User(this.userName, this.passWord, this.sex, this.nickName, this.age);
		}
		return newUser;

	}// RegisterStuff(.......注册信息.......)_END

	private static String getUserName() {
		return userName;
	}

	private static String getPassWord() {
		return passWord;
	}

	private static String getSex() {
		return sex;
	}

	private static String getNickName() {
		return nickName;
	}

	private static int getAge() {
		return age;
	}

	public RegisterStuff() {
	}

	// 注册
	public static void register() {
		System.out.println("请输入您的>>账户名<<>>密码<<>>确认密码<<>>性别<<>>昵称<<>>年龄<<\n以空格隔开~💗(*^_^*)💗");
		RegisterStuff rs = new RegisterStuff();
		User newUser = rs.newRegisterStuff(in.next(), in.next(), in.next(), in.next(), in.next(), in.nextInt());
		if (newUser != null) {
			registerToUser(newUser);// 在将注册信息注册入了用户数据库后. 可能会为null 此时在这个方法中判断
			printOK(newUser);// 登陆成功提示,并提示用户的昵称等.
		}
	}

	// 注册完成后用户数据放入UserDatabase
	public static void registerToUser(User newUser) {

		UserDatabase.setUserNameList(newUser.getUserName());
		UserDatabase.setUserInstance(newUser);
		HashMap<Long, User> userDatabaseInRegister = UserDatabase.getUserDataMap();
		long ID = newUser.getID_number();
		userDatabaseInRegister.put(ID, newUser);
		UserDatabase.setUserData(userDatabaseInRegister);

		// 注册信息已送入UserDatabase 可以将用户数据使用IO写入txt文件
	}

//	// 最后补的 暂未加入
//	public static void afterRegister() {
//		printOK();
//		BookShelf bs = new BookShelf();
//		bs.BookShelfInit();
//		bs.getAllCategory();
//		System.out.println("请问你要查阅哪些书籍?或哪一本?");
//	}

	// 注册成功之后
	private static void printOK(User user) {

		System.out.println("注册成功!");
		HashMap<Long,User> userInstanceInRegister = UserDatabase.getUserDataMap();
		User thisUser = userInstanceInRegister.get(user.getID_number());
		
		System.out.println("***"+thisUser+"***");

	}

	// 从数据库取出用户名,查看这次的用户是否存在重复
	public int userNameAgain(String userName) {
		// 从用户数据库List获取已经存在的账户对象
		Set<String> userAlreadyHave = UserDatabase.getUserNameList();

		// 从数据库取出,去判定用户名是否存在,单独写个方法
		for (String alreadyHaveName : userAlreadyHave) {
			if (userName.equals(alreadyHaveName)) {
				return 1;// 重复用户名返回1
			}
		}
		// 不重复用户名返回0
		return 0;
	}

	// 重新写入passWord属性
	public boolean passWordAgain(String password1, String password2) {

		if (password1.equals(password2)) {
			System.out.println("本次密码核对正确!");
			return true;
		}
		return false;
	}

	// 重新写入sex属性
	private boolean sexAgain(String sex_) {
		if ((sex_.equals("男") || sex_.equals("女") || sex_.equals("man") || sex_.equals("man") || sex_.equals("woman")
				|| sex_.equals("boy") || sex_.equals("girl"))) {
			return true;
		}
		return false;
	}

	// 重新写入nickName属性
	public boolean nickNameAgain(String nickname_) {

		if (nickname_ != "" || nickname_ != null || nickname_.length() != 0) {
			return true;
		}
		return false;
	}

	// 重新写入age属性
	public boolean ageAgain(int age_) {
		if (age_ < 999 && age_ > 0) {
			return true;
		}
		return false;
	}

}

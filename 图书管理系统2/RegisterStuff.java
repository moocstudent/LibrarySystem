package 图书管理系统2;

import java.util.ArrayList;
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
	private static int identity;

	public void newRegisterStuff(String username, String password1, String password2, String sex, String nickname,
			int age, int identity) {

		String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(username);
		boolean b = match.matches();

		String userName_;
		boolean userNameCheckOk = false;
		boolean userNamePassed = (username != "" || username != null || username.length() != 0);
		if (userNamePassed) {
			if (!b) {
				System.out.println("用户名只能由数字字母和下划线组成!请重新输入!💗");
				register();
				// return;
			} else {
				// 如果符合了用户名格式,继而判定用户名重复与否
				while (userNameAgain(username) == 1) {
					System.err.println("该用户名已经存在,请重新注册~💗");
					register();
					return;
				}
				if (userNameAgain(username) == 0) {// 不重复则通过,并赋值给this.userName
					userNameCheckOk = true;
					this.userName = username;
				}
			}

		}

		// 判定密码
		boolean passwordPassed = password1.equals(password2);
		String password_;
		if (passwordPassed) {
			this.passWord = password1;
		} else {
			while (!passwordPassed) {
				System.err.println("密码两次不一致,请核对后再次输入!(只输入两次密码💗)");
				password_ = in.next();
				String password2_ = in.next();
				passwordPassed = passWordAgain(password_, password2_);
				if (passwordPassed) {
					this.passWord = password_;
				}
			}
		}

		// 判定性别输入 (不足)英文只支持小写.
		boolean sexPassed = (sex.equals("男") || sex.equals("女") || sex.equals("man") || sex.equals("woman")
				|| sex.equals("boy") || sex.equals("girl"));
		boolean M_sex = (sex.equals("男") || sex.equals("man") || sex.equals("boy"));
		boolean F_sex = (sex.equals("女") || sex.equals("woman") || sex.equals("girl"));
		String sex_;
		if (sexPassed) {
			if (M_sex) {
				this.sex = "M";
			}
			if (F_sex) {
				this.sex = "F";
			}
		} else {
			while (!sexPassed) {
				System.err.println("性别填写错误,请再次填写:");
				sex_ = in.next();
				sexPassed = sexAgain(sex_);
				if (sexPassed) {
					if (M_sex) {
						this.sex = "M";
					}
					if (F_sex) {
						this.sex = "F";
					}
				}
			}
		}
		// 判定昵称合法性
		boolean nickNamePassed = (nickname != "" || nickname != null || nickname.length() != 0);
		if (nickNamePassed) {
			this.nickName = nickname;
		} else {
			while (!nickNamePassed) {
				System.err.println("昵称为空,请再次填写:");
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
				System.err.println("只接受0岁以上儿童以及999岁以内的银河系生物!请再次输入年龄!");
				int age_ = in.nextInt();
				agePassed = ageAgain(age_);
				if (agePassed) {
					this.age = age_;
				}
			}
		}
		// 判定身份证后4位长度,注意+""时一定要使用括号将int类型和""括在一起.否则会错误
		boolean identityPassed = ((identity + "").length()) == 4;
		if (identityPassed) {
			this.identity = identity;
		} else {
			while (!identityPassed) {
				System.err.println("请输入**4位**身份证尾号~!💗");
				int identity_ = in.nextInt();
				identityPassed = identityAgain(identity_);
				if (identityPassed) {
					this.identity = identity_;
				}
			}

		}
		// 结束了所有数据的判断
		/////////////////////////////////////////////////////////////////////////////////
		boolean AuthPassed = (userNameCheckOk == true) && userNamePassed && b && passwordPassed && sexPassed
				&& nickNamePassed && agePassed && identityPassed;

		// 如果注册成功则创建新user对象.
		String register_SQL = null;
		if (AuthPassed) {
			ArrayList<Object> userDatas = new ArrayList<>();
			userDatas.add(getUserName());
			userDatas.add(getPassWord());
			userDatas.add(getSex());
			userDatas.add(getNickName());
			userDatas.add(getAge());
			userDatas.add(getIdentity());
			register_SQL = "insert into lib_users values(seq_lib_users.nextval,?,?,?,?,?,?,sysdate,seq_card_id.nextval)";
			MariaSQLManager.sql_Handler(register_SQL,userDatas);
			String card_id_SQL = "select card_id from lib_users where user_name = ?";
			MariaSQLManager.sql_Handler(card_id_SQL,getUserName());
			System.out.println("这是您的借阅卡,"+getNickName()+",卡号为:" + getCardId() + ",可用于刷卡登陆,请保管好谢谢~!💗");
		}

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

	private static int getIdentity() {
		return identity;
	}

	public RegisterStuff() {
	}

	// 注册
	public static void register() {
		System.out.println("请输入您的>>账户名<<>>密码<<>>确认密码<<>>性别<<>>昵称<<>>年龄<<>>身份证后4位<<\n以空格隔开~💗(*^_^*)💗");
		RegisterStuff rs = new RegisterStuff();
		rs.newRegisterStuff(in.next(), in.next(), in.next(), in.next(), in.next(), in.nextInt(), in.nextInt());
		// if (newUser != null) {
		// registerToUser(newUser);// 在将注册信息注册入了用户数据库后. 可能会为null 此时在这个方法中判断
		// printOK(newUser);// 登陆成功提示,并提示用户的昵称等.
		// }
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

	// // 最后补的 暂未加入
	// public static void afterRegister() {
	// printOK();
	// BookShelf bs = new BookShelf();
	// bs.BookShelfInit();
	// bs.getAllCategory();
	// System.out.println("请问你要查阅哪些书籍?或哪一本?");
	// }

	// 注册成功之后
	private static void printOK(User user) {

		System.out.println("注册成功!");
		HashMap<Long, User> userInstanceInRegister = UserDatabase.getUserDataMap();
		User thisUser = userInstanceInRegister.get(user.getID_number());

		System.out.println("***" + thisUser + "***");

	}

	// 从数据库取出用户名,查看这次的用户是否存在重复 1为重复,先默认为重复,当判定无此用户名可注册时,赋值0
	public static Integer nameCheckNum = null;
	public static String databaseUserName = null;

	public String getdbUserName() {
		return databaseUserName;
	}

	public int getNameCheckNum() {
		return nameCheckNum;
	}

	public void setNameCheckNum(int nameCNum) {
		this.nameCheckNum = nameCNum;
	}

	public void setDbUserName(String dbName) {
		this.databaseUserName = dbName;
	}

	public int userNameAgain(String userName) {
		// 从用户数据库List获取已经存在的账户对象
		String userName_SQL = "select user_name from lib_users where user_name = '" + userName + "'";
		// MariaSQLManager.sql_Handler(input_SQL);
		MariaSQLManager.sql_Handler(userName_SQL);

		// Set<String> userAlreadyHave = UserDatabase.getUserNameList();
		return getNameCheckNum();
		// return 1;
		// 从数据库取出,去判定用户名是否存在,单独写个方法
		// for (String alreadyHaveName : userAlreadyHave) {
		// if (userName.equals(alreadyHaveName)) {
		// return 1;// 重复用户名返回1
		// }
		// }
		// 不重复用户名返回0
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

	// 重新写入identity身份证后4位
	public boolean identityAgain(int identity_) {
		if ((identity_ + "").length() == 4) {
			return true;
		}
		return false;
	}

	public static long CardId;
	public void setCardId(long data) {
		this.CardId= data;
	}

	public long getCardId() {
		return CardId;
	}
}

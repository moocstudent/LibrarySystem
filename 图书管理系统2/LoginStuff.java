package 图书管理系统2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class LoginStuff {
	static Scanner in = new Scanner(System.in);

	// 登陆方式选区,通过卡号呢还是用户名密码
	public static void loginSelect() {
		System.out.println("1.刷卡登陆.<输入卡号>\n2.用户名密码登陆.\n#.返回上级");
		String loginSelectNum = in.next();
		boolean numRight = loginSelectNum.equals("1") || loginSelectNum.equals("2") || loginSelectNum.equals("#");
		if (numRight) {
			if (loginSelectNum.equals("1")) {
				LoginStuff.loginWithID();
			}
			if (loginSelectNum.equals("2")) {
				LoginStuff.loginWithUserAndPassword();
			}
			if (loginSelectNum.equals("#")) {
				Service.getHello();
			}
		} else {
			System.out.println("输入错误,请输入1或2或#.💗");
			System.out.println();
			loginSelect();
		}
	}

	// 执行login(ID)前必须执行该方法
	// 使用ID卡号登陆
	public static void loginWithID() {
		System.out.println("请输入您的卡号,以#号结束💗,(或只输入#返回上一层)");
		String ID_numberStr = "";
		String nextNumber = in.next();
		int OriginalIDlength = Library.IDLength;
		ID_numberStr = nextNumber.trim();
		boolean IDPassed = false;

		if (ID_numberStr.endsWith("#")) {
			if (ID_numberStr.length() == 1 && ID_numberStr.equals("#")) {
				loginSelect();
				return;
			}
			ID_numberStr = ID_numberStr.replace("#", "");
			if (ID_numberStr.length() != OriginalIDlength) {
				IDPassed = false;
				System.out.println("正确卡号长度为" + OriginalIDlength + "位.请重新输入:💗");
			}
			if (ID_numberStr.length() == OriginalIDlength) {
				IDPassed = true;
			}

		}
		if (IDPassed) {
			long ID_number = (long) Long.valueOf(ID_numberStr);
			login(ID_number);
		} else {
			loginWithID();
		}

	}

	// 通过用户名及密码登陆的方法,这一方法只是判定其格式,之后再调用了 login(inputUserName, inputPassWord)
	public static void loginWithUserAndPassword() {
		System.out.println("请输入用户名及密码登陆.💗,(或只输入#返回上一层)");
		String inputUserName = in.next();
		if(inputUserName.equals("#")) {
			loginSelect();
			return;
		}
		String inputPassWord = in.next();
		boolean regexFailed = inputUserName == null || inputUserName == "" || inputUserName.length() == 0
				|| inputPassWord == null || inputPassWord == "" || inputPassWord.length() == 0;
		while (regexFailed) {
			System.err.println("用户名或密码不能为空!💗");
			loginWithUserAndPassword();
		}

		if (!regexFailed) {
			login(inputUserName, inputPassWord);
		}

	}

	// 登陆方式的选择2, 通过用户名和密码登陆 之前已经使用loginWithUserAndPassword()进行了用户名和密码的接受和格式判定
	// 而这一步是判定用户数据库的userList中和输入的用户名密码进行比对,如果正确则提示登陆成功,
	// 如果对比失败则重新调用loginWithUserAndPassword()方法进行用户名和密码的接受和判定,其后再这个login方法进行比对.循环往复

	public static void login(String userName, String passWord) {
		Set<User> userList = UserDatabase.getUserInstance();
		boolean userInList = false;
		boolean userAuthPassed = false;
		String thisNick = null;
		String pass = null;
		User thisUser_ = null;
		Iterator<User> userIt = userList.iterator();
		while (userIt.hasNext()) {
			thisUser_ = userIt.next();
			if (userName.equals(thisUser_.getUserName())) {
				pass = thisUser_.getPassWord();
				userInList = true;
			}
			if (userInList) {
				if (passWord.equals(pass)) {
					userAuthPassed = true;
					thisNick = thisUser_.getNickName();
					break;
				}
			}
		}
		// 登陆通过之后
		if (userAuthPassed) {
			System.out.println(thisNick + "登陆成功!");
			System.out.println("快和小伙伴们一起玩耍(看书)吧!💗");
			MemberService.showMemberSerivce(thisUser_);
		} else {
			System.out.println("账号或密码错误,请重新登陆!💗");
			loginWithUserAndPassword();
		}

	}

	// 通过用户ID进行登陆,先通过User中的IDNumberSet这个Set集合进行取出,
	// 使用了IDNumberJudge(Long ID)方法进行了"是否有这个ID"的比对
	public static void login(long ID_Number) {
		long inputID = User.getID_number();
		// 该boolean值就是去确定是否有这个ID号码,有则为true.没有这个ID也就是
		// 说明用户ID是不存在的,不能登陆,其后可以再设置让其进行注册
		boolean IDReady = User.IDNumberJudge(inputID);
		if (IDReady) {
			HashMap<Long, User> userData = UserDatabase.getUserDataMap();
			User thisUser = userData.get(inputID);
			System.out.println("------------------------");
			System.out.println(thisUser);
			System.out.println("刷卡成功,可以随意看书啦.当您离开记得再次刷卡以扣除时间段内消费!💗");
			System.out.println("------------------------");
			MemberService.showMemberSerivce(thisUser);
		} else {
			System.out.println("抱歉😭,没有找到关于您卡号的信息!请稍后再试.如需帮助请联系管理员.💗");
			System.out.println("或者您也可以进行使用面板的1号选项进行注册.💗");
			Service.getHello();
		}
	}

}

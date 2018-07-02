package 图书管理系统2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class User implements UserBehavior {

	static Scanner in = new Scanner(System.in);
	private static Set<Long> IDNumberSet = new HashSet<Long>();

	private static Set<Long> getIDNumberSet() {
		return IDNumberSet;
	}

	// 这样调用虽然可以核对出"有没有"这个ID,但是却不能很好地去调出用户信息.应该直接去Userdatabase中调用UserData这个
	// HashMap,并通过get(key)的方式获取User对象,并输入实例化,此时该user对象已经toString重写了
	// 也可以在这判定有没有之后
	public static boolean IDNumberJudge(Long inputID) {
		Iterator<Long> idIterator = IDNumberSet.iterator();
		while (idIterator.hasNext()) {
			long IDInSet = (long) idIterator.next();
			if (inputID.equals(IDInSet)) {
				return true;
			}
		}
		return false;
	}

	private static long ID_number; // 随机生成ID Set集合

	public static long getID_number() {
		return ID_number;
	}

	// String UserSelfName;
	String userName;
	String passWord;
	String sex;
	String nickName;// 昵称 , 用于展示和提示
	int age;

	
	Random randID = new Random();
	// 图书馆卡卡号随机生成器 不重复
	public long IDGenerator() {
		long RandomID = randID.nextLong();
		RandomID = Math.abs(RandomID);
		String RdIDStr = RandomID + "";
		long RIDLength = RdIDStr.length();
		char[] RdIDChar = RdIDStr.toCharArray();
		// 选择排序
		FOR1: for (int i = 0; i < RIDLength - 1; i++) {
			for (int j = i + 1; j < RIDLength; j++) {
				if (RdIDChar[i] == ('4') || RdIDChar[j] == ('4')) {
					RdIDChar[i] = (char) ('5' + Math.random() * 5);
					continue FOR1;
				}

				if (RdIDChar[i] > RdIDChar[j]) {
					char tempChar = RdIDChar[j];
					RdIDChar[j] = RdIDChar[i];
					RdIDChar[i] = tempChar;
				}
			}
		}
		String order_RdIDStr = String.valueOf(RdIDChar);
		long RdStrLen = order_RdIDStr.length();
		if (RdStrLen > 10) {
			order_RdIDStr = 8 + order_RdIDStr.substring(0, 9);
		}
		long order_RandomID = Long.parseLong(order_RdIDStr);
		if (!IDNumberSet.add(order_RandomID)) { // 应该去遍历Set比对后再存入??
			IDGenerator();
		} 
		
//		else {
//			IDNumberSet.add(order_RandomID);// 如果能存入则存入该不同的ID
//		}
		return order_RandomID;
	}

	public User() {

	}

	private void setID_number(long iD_number) {
		ID_number = iD_number;
	}

	

	//
	public User(String userName, String passWord, String sex, String nickName, int age) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.nickName = nickName;
		this.sex = sex;
		this.age = age;
		this.ID_number = IDGenerator();
		IDNumberSet.add(ID_number);
	}

	public String toString() {
		return "已经查询到用户表,但部分信息已加密.\n" + "借阅卡卡号:" + ID_number + "\n" + "账户名:" + userName + "\n" + "昵称:" + nickName
				+ "\n";
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

	public String getPassWord() {
		// TODO Auto-generated method stub
		return passWord;
	}

	public String getNickName() {
		// TODO Auto-generated method stub
		return nickName;
	}

	ArrayList<String> borrowedBooks = new ArrayList<String>();

	public void setBorrowedBooks(ArrayList<String> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	public ArrayList<String> getBorrowedBooks() {
		return borrowedBooks;
	}

	@Override
	public void borrow(Books book) {
		// 已经判定了书的存在才传入这里
		
		book.beBorrowed = 1;
		System.out.println("这是您的《" + book.bookName + "》,请拿好!");
		System.out.println("您的阅读次数-1");
		ArrayList<String> borBooks = this.getBorrowedBooks();
		borBooks.add(book.bookName);
		setBorrowedBooks(borBooks);

	}

	@Override
	public void ask(Service serv) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveBack(Books book, boolean bookIsBack) {
		if (bookIsBack) {
			// backBookInstance.beBorrowed -= 1; //这种也行,直接去掉下面两行换这行
			Integer beBorrowedNum = book.getBeBorrowed();
			beBorrowedNum = 0;
			System.out.println(book.bookName+"还书成功!📕📕📕,想看再来借阅哦,💗");
		}else {
			System.out.println("还书操作未执行.BUG,请联系服务员询问具体原因.");
		}
	}


}

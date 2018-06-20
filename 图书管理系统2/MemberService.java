package 图书管理系统2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class MemberService {

	static Scanner in = new Scanner(System.in);

	public static void showMemberSerivce(User thisUser_) {
		
		System.out.println("您好," + thisUser_.getNickName() + ",这是会员服务栏目,您可享受一下服务,Enjoy it!💗");
		System.out.println("1.借书\t\t2.还书\n3.点个咖啡\t\t4.上网冲浪\n5.健身运动\t\t6.图书馆留言\n#.返回上级目录(退出登陆)");
		String serNum = in.next();
		switch (serNum) {
		case "1":borrowBookSer(thisUser_);
			break;
		case "2":giveBackBookSer(thisUser_);
			break;
		case "3":
			break;
		case "4":
			break;
		case "5":
			break;
		case "6":
			break;
		case "#":LoginStuff.loginSelect();
			break;
		default:
			System.out.println("请输入正确的服务选择~!💗");
			showMemberSerivce(thisUser_);
			break;
		}
	}
	public static void borrowBookSer(User user) {
		System.out.println("您好💗"+user.getNickName()+",请问您想借阅哪本书呢?💗");
		HashMap<String, Books> books = BookShelf.getBookMap();
		Set<String> bookNames = books.keySet();
		for(String bns : bookNames) {
			System.out.print(bns+" : "+books.get(bns).beBorrowed);
		}
		
		System.out.println("输入书名,借阅书籍.💗");
		String wantedBook = in.next();
		user.borrow(books.get(wantedBook));
		showMemberSerivce(user);
	}
	
	public static void giveBackBookSer(User user) {
		System.out.println("您好💗"+user.getNickName()+",请问您想还哪本书呢?💗");
		ArrayList<String> userBorrowedBooks = user.getBorrowedBooks();
		if(userBorrowedBooks == null) {
			System.out.println("抱歉您没有书要还~💗");
			return;
		}
		System.out.println("您借阅的书此时还有如下:");
		int hasBorrowedCount = 0;
		for(String books:userBorrowedBooks) {
			hasBorrowedCount++;
			System.err.println(books);
		}
		if(hasBorrowedCount != 0) {
			System.out.println("请输入您要还的书的书名~:");
			String giveBackBookName = in.next();
			//从用户已借阅列表中删除这本书
			boolean giveback = userBorrowedBooks.remove(giveBackBookName);
			HashMap<String, Books> bookInstanceMap =BookShelf.getBookMap();
			Books backBookInstance = bookInstanceMap.get(giveBackBookName);
			
			user.giveBack(backBookInstance, giveback);
			showMemberSerivce(user);
		}else {
			System.err.println("Sorry,您没有书要还~💗");
			showMemberSerivce(user);
		}
	}

}

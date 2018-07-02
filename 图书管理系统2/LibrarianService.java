package 图书管理系统2;

import java.awt.print.Book;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

public class LibrarianService implements Service,Library {

	private static Scanner in = new Scanner(System.in);
	private String userSelfName;

	public String getUserSelfName() {
		return userSelfName;
	}
	
	// getHello()调用给用户看的主面板,选项信息
	public static void getHello() {

		boolean helloRun = true;
		while (helloRun) {
			System.out.println("------------------------------");
			System.out.println("您好 ,我是" + libraryName + "的图书管理员,请问有什么可以帮您的吗?");
			System.out.println("1:想办读书借阅卡.<注册>");
			System.out.println("2:已有账户,使用借阅卡号或账户名称进行登陆.<登陆>");
			System.out.println("3.想看下有什么类别的书再办卡.");
			System.out.println("4.只想买几本书,不想办卡.");
			System.out.println("5.查看" + libraryName + "介绍.");
			System.out.println("6.管理员操作.");
			System.out.println("#.离开.");
			
			
			System.out.println("------------------------------");
			String select = in.next();
			if (select.equals("1")) {
				RegisterStuff.register();
			} else if (select.equals("2")) {// 已有借阅卡,之后跳转到会员服务信息和功能处 MemberService
				LoginStuff.loginSelect();
			} else if (select.equals("3")) {
				System.out.println("------------------------------");
				BookShelf.getAllCategory();
				System.out.println();
			} else if (select.equals("4")) {
				checkBook();
			} else if (select.equals("5")) {
				System.out.println("💗💗💗" + libraryName + "💗💗💗");
				System.out.println(libraryName + "是一家寓教于乐,集咖啡吧及上网冲浪于一体的多功能全方位的图书馆.");
				System.out.println();
			}else if(select.equals("6")) {
				MariaSQLManager.sql_Helper();
				break;
			} else if (select.equals("#")) {
				System.out.println("(*^_^*)欢迎再来哦!💗");
				helloRun = false;
				in.close();
			} else {
				System.out.println("输入错误请重新输入:");
				getHello();
			}
		}
	}

	// 登陆之后的一些事情,比如直接给用户显示一下书的分类,或者其他服务项目
	public final void afterLogin() {
	}

	// 查书,通过输入书名查询书的具体信息,没有则提示没有之类的,并持续让用户可查其他书名,或者不再查询
	public static void checkBook() {

		System.out.println("请给出书名,我给您查下.不用书名号.💗");
		String inputBookName = in.next();
		System.out.println("------------");
//		BookShelf.getBook(inputBookName);
		BookShelf.getBookFromBooksDB(inputBookName);
//		MariaSQLManager.sql_Handler(inputBookName);

		// 查询其他书名
		System.out.println("您还想查看其他书的信息吗?");
		System.out.println("y:是的.");
		System.out.println("n:不了.");
		String selectYN = in.next();
		boolean isRightYNselect = selectYN.equals("y") || selectYN.equals("n");
		while (!isRightYNselect) {
			System.out.println("您的输入有误,请重新输入!");
			selectYN = in.next();
			isRightYNselect = selectYN.equals("y") || selectYN.equals("n");
		}
		if (isRightYNselect) {
			if (selectYN.equals("y")) {
				checkBook();
			} else if (selectYN.equals("n")) {
				System.out.println("好的知道了,想买了再来哦.💗");
			} else {
				System.out.println("输入错误💗");
			}
		}

	}

	// 欢迎类信息
	public void welcome() {

		System.out.println("欢迎来到-->" + libraryName + "💗");
		System.out.println("\t———— 愿书籍点亮你前进的道路");

		// getHello()调用给用户看的主面板,选项
		getHello();

	}

}

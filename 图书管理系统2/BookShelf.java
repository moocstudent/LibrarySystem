package 图书管理系统2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * 实现图书馆Library接口,形象地展示在图书馆中的书架.
 * 
 * @author SeeClanUkyo
 *
 */
public class BookShelf implements Library {
	static Scanner in;
	// 通过String书名 找到Books书的对象实例. - toString()改写后输出书的全部信息.

	
	private static HashMap<String, Books> bookMap = new HashMap<>();
	public static HashMap<String, Books> getBookMap() {
		return bookMap;
	}
	private static HashMap<String, HashMap<String, Books>> categoryMap = new HashMap<>();

	private static Books book1 = new Books("历史类", "这个历史很靠谱", "袁腾飞", "武汉出版社", 2012, 38.00);
	private static Books book2 = new Books("历史类", "万历十五年", "黄仁宇", "三联书店", 1990, 18.00);

	private static Books book3 = new Books("文学类", "红高粱家族", "莫言", "天马书店", 1987, 39.00);
	private static Books book4 = new Books("文学类", "倾城之恋", "张爱玲", "天马书店", 1943, 26.00);

	private static Books book5 = new Books("计算机类", "Spring实战", "Craig Walls", "Manning", 2011, 60.00);
	private static Books book6 = new Books("计算机类", "HTTP权威指南", "David Gourley", "人民邮电出版社", 2012, 109.00);

	// 设置书架 每种书的书架在各自的种类的书架上
	public static void setCategoryShelf(String categoryName, String bookName, Books book) {
		// 计算机类只存入计算机类的HashMap
		// 书名是唯一的
		HashMap<String, Books> bookM = categoryMap.get(categoryName);
		if (bookM == null) {
			bookM = new HashMap<String, Books>();
			categoryMap.put(categoryName, bookM);
		}
		bookM.put(bookName, book);
		bookMap.put(bookName, book);
	}

	// 遍历输出所有分类
	public static void getAllCategory() {
		Set<String> categorySet = categoryMap.keySet();
		for (String categoryName : categorySet) {
			System.out.println(categoryName);
		}
		System.out.println("还想查看某分类下的具体书籍吗?请输入分类名称:");
		in = new Scanner(System.in);
		String inputCategoryName = in.next() + "";// 未进行书类判断
		if (inputCategoryName == null || inputCategoryName == "" || inputCategoryName.length() == 0) {
			System.out.println("输入为空~请重新输入:");
			getAllCategory();
		}
		getCategoryBooks(inputCategoryName);
	}

	// 通过分类查找书名 还没完成最终效果,只是输出了集合样式
	public static void getCategoryBooks(String categoryName) {
		Set<String> categoryN = categoryMap.keySet();
		HashMap<String, Books> rightCategory = null;
		int count = 0;
		for (String cate : categoryN) {
			if (categoryName.equals(cate)) {
				rightCategory = categoryMap.get(categoryName);
				count++;
				break;
			}
		}
		if (count != 0) {
			Collection<Books> booksInstance = rightCategory.values();
			System.out.println(categoryName+"下有如下书籍:");
			for (Books book : booksInstance) {
				System.out.println();
				System.out.println("😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄\n");
				
				System.out.println(book);
				System.out.println("😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄😄");
				System.out.println();
			}
		} else {
			System.out.println("输出分类错误了呢~💗");
			getAllCategory();

		}
	}

	// 通过书名查书信息 (待完善)
	public static void getBook(String bookName) {
		Set<String> bookNames = bookMap.keySet();
		Books foundBooks;
		int count = 0;
		for (String bn : bookNames) {
			if (bookName.equals(bn)) {
				count++;
				foundBooks = bookMap.get(bookName);
				System.out.println(foundBooks);
				break;
			} else {

			}
		}
		if (count == 0) {
			System.err.println("没有您要找的这本书哦!💗");
		} else {
			System.out.println("按照价格请到服务台结算,或通过" + libraryName + "的电脑服务进行网购或购买电子版.");
			System.out.println();
		}
	}

	// 初始化书架
	public static void BookShelfInit() {
		setCategoryShelf("历史类", "这个历史很靠谱", book1);
		setCategoryShelf("历史类", "万历十五年", book2);

		setCategoryShelf("文学类", "红高粱家族", book3);
		setCategoryShelf("文学类", "倾城之恋", book4);

		setCategoryShelf("计算机类", "Spring实战", book5);
		setCategoryShelf("计算机类", "HTTP权威指南", book6);
	}

	public static void getBookFromBooksDB(String inputBookName) {
		String sql_BS = "select * from books where book_name = '"+inputBookName+"'";
		MariaSQLManager.sql_Handler(sql_BS);
	}

}

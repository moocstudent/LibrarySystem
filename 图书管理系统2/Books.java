package 图书管理系统2;

/**
 * 放书的那一类
 * 
 * @author SeeClanUkyo
 *
 */
public class Books {

	String categoryName;
	String bookName;
	String authorName;
	String bookConcernName;
	Integer beBorrowed = null;
	int amountOfAllLibraryBooks;// 图书馆藏书量总量
	int amountOfOneCategoryBooks;// 某种类书的总量
	int amountOfOneNameBooks;// 某名字的书的总量
	int issueYear;
	double price;

	public Integer getBeBorrowed() {
		return beBorrowed;
	}
	public Books(String categoryName, String bookName, String authorName, String bookConcernName, int issueYear,
			double price) {
		this.categoryName = categoryName;
		this.bookName = bookName;
		this.authorName = authorName;
		this.bookConcernName = bookConcernName;
		this.issueYear = issueYear;
		this.price = price;
		this.beBorrowed = 0;// 0为未被借阅,书初始化时都未被借阅
	}

	public String toString() {
		String borrowedInfo = "";
		if (beBorrowed == 0) {
			borrowedInfo = "未借阅";
		} else {
			borrowedInfo = "已借阅";
		}
		return "书名:" + bookName + "\n" + "类别:" + categoryName + "\n" + "作者:" + authorName + "\n" + "出版社:"
				+ bookConcernName + "\n" + "发行时间:" + issueYear + "\n" + "价格:" + price + "\n" + borrowedInfo + "\n";
	}

}

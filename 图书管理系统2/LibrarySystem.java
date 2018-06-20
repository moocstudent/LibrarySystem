package 图书管理系统2;

import java.util.Scanner;

public class LibrarySystem {

	Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
	
		UserDatabase.UserDatabaseInit();
		BookShelf.BookShelfInit();
		Service humanServ = new LibrarianService();
		humanServ.welcome();
	
		
	}

}

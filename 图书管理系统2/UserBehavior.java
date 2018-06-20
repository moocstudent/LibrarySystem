package 图书管理系统2;

public interface UserBehavior {
	void borrow(Books book);
	void giveBack(Books book, boolean bookIsBack);
	void ask(Service serv);
}

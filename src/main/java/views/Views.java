 
package views;

public class Views {

    public static Views VIEWS = new Views();

	public String LOGIN = "WEB-INF\\login.jsp";
	public String MESSAGE_BOX = "WEB-INF\\message-box.jsp";
	public String REGISTER = "WEB-INF\\register.jsp";
	public String RESETPASSWORD = "WEB-INF\\resetpassword.jsp";
 

	public Admin ADMIN = new Admin ();
	public Classteacher CLASSTEACHER = new Classteacher ();
	public Hod HOD = new Hod ();
	public Student STUDENT = new Student ();
	public Teacher TEACHER = new Teacher ();

}
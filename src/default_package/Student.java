import java.util.ArrayList;

public class Student {

	private String fname;
	private String lname;
	private long idNum;
	private int grade;
	private Courses course;
	private ArrayList<Courses> takenCourses = new ArrayList<Courses>();
	
	
	
	public void Student(String fn, String ln, long iN, int g)
	{
		this.fname =fn;
		this.lname = ln;
		this.idNum = iN;
		this.grade = g;
	}
	
	public void setCourses(String cID, String cTitle, double cr)
	{
		
		course = new Courses();
		course.Courses(cID, cTitle, cr);
		takenCourses.add(course);
		
	}
	
	public String getInfo() {
		String st="Name: " + fname + " " + lname + "\n";
		st=st+"ID Number: " + idNum + "\n";
		st=st+"Grade: " + grade;
		return st;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public String toString()
	{
		String st="Name: " + fname + " " + lname + "\n";
		st=st+"ID Number: " + idNum + "\n";
		for(int i = 0; i<takenCourses.size(); i++) {
			st=st+takenCourses.get(i).toString();
		}
		
		return st;
	}
	
	public ArrayList<Courses> getCourses(){
		return takenCourses;
	}
	
}

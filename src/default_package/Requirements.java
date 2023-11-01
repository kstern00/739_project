
public class Requirements extends Courses{
	
	private int grade;
	
	public void Courses(String cID, String cTitle, int g, String dep) {
		this.courseID = cID;
		this.courseTitle = cTitle;
		this.credit = 0.0;
		this.department = dep;
		this.grade= g;
	}
	
	public int getGrade() {
		return this.grade;
	}
	
	public String toString() {
		String str = "Course ID: " + getCourseID();
		str = str+ ", Course Title: " + getTitle();
		str = str+ ", Course Department: " + getDepartment() +"\n";
		return str;
	}
	
	

}

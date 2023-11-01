

public class Courses {
	public String courseID;
	public String courseTitle;
	public double credit;
	public String department;
	
	public void Courses(String cID, String cTitle, double cr, String dep) {
		this.courseID = cID;
		this.courseTitle = cTitle;
		this.credit = cr;
		this.department = dep;
	}
	
	public String getTitle() {
		return this.courseTitle;
	}
	
	public String getCourseID() {
		return this.courseID;
	}
	
	public double getCredit() {
		return this.credit;
	}
	public String getDepartment() {
		return this.department;
	}
	
	public void Courses(String cID, String cTitle, double cr) {
		this.courseID = cID;
		this.courseTitle = cTitle;
		this.credit = cr;
		this.department = "NA";
		
	}
	
	public String toString() {
		String str = "Course ID: " + getCourseID();
		str = str+ ", Course Title: " + getTitle() +"\n";
		return str;
	}
	

}

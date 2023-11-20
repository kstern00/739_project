package full_stack.demo;
import jakarta.persistence.*;

public class Courses {
	//@Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "courseID")
	public String courseID;
//	@Column(name = "courseTitle")
	public String courseTitle;
//	@Column(name = "credit")
	public double credit;
//	@Column(name = "department")
	public String department;
	public String finalGrade;
	
	public void Courses(String cID, String cTitle, double cr, String dep, String fg) {
		this.courseID = cID;
		this.courseTitle = cTitle;
		this.credit = cr;
		this.department = dep;
		this.finalGrade = fg;
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

	public String getCourseGrade(){
		return finalGrade;
	}
	
	public void Courses(String cID, String cTitle, double cr, String fg) {
		this.courseID = cID;
		this.courseTitle = cTitle;
		this.credit = cr;
		this.department = "NA";
		this.finalGrade = fg;
		
	}
	
	public String toString() {
		String str = "Course ID: " + getCourseID();
		str = str+ ", Course Title: " + getTitle() +"\n";
		str = str+ ", Course Grade " + getCourseGrade() +"\n";
		return str;
	}
	

}


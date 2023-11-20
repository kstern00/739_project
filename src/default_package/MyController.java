package full_stack.demo;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping(value = "/form")
public class MyController {

    private final DataService dataService;
    private final String courseNames [] = {"English", "Math", "Social Studies", "Science", "Physical Education", "Health"};

    public MyController(DataService d){
        this.dataService = d;
    }
    @GetMapping
    public String getForm() {
        
        return "form.html";
    }

    @GetMapping
    public ResponseEntity<String> missingReq(){
        ArrayList<Student> sData = new ArrayList<Student>();
        ArrayList<Courses> courseList = new ArrayList<Courses>();
        ArrayList<Requirements> reqList = new ArrayList<Requirements>();

        String missingReq = "", results="";
		int gd, count = 0;
		
        sData = dataService.getStudentData();
        courseList = dataService.getCourseData();
        reqList = dataService.getReqData();

		ArrayList<Courses> studentCourses;

        for(Student s: sData) {
			gd = s.getGrade();
			int[] reqs = {0,0,0,0,0};
			studentCourses = new ArrayList<Courses>(s.getCourses());
			for(Requirements r: reqList) {
				if(r.getGrade()==gd) {
					//determine which department it is looking at
					if(r.getDepartment().equals("English")) {
						reqs[0]+=courseTracker(r.getCourseID(), studentCourses);	
					}
					else if(r.getDepartment().equals("Math")) {
						reqs[1]+=courseTracker(r.getCourseID(), studentCourses);
					}
					else if(r.getDepartment().equals("Social Studies")) {
						reqs[2]+=courseTracker(r.getCourseID(), studentCourses);	
					}
					else if(r.getDepartment().equals("Science")) {
						reqs[3]+=courseTracker(r.getCourseID(), studentCourses);	
					}
					else if(r.getDepartment().equals("Physical Education") || r.getDepartment().equals("Health")) {
						reqs[0]+=courseTracker(r.getCourseID(), studentCourses);	
					}		
				}
			}
			//if it doesn't find the courses in the requirements list, could still be okay
			for(Courses c: courseList) {
				if(c.getDepartment().equals("English")) {
					reqs[0]+=courseTracker(c.getCourseID(), studentCourses);	
				}
				else if(c.getDepartment().equals("Math")) {
					reqs[1]+=courseTracker(c.getCourseID(), studentCourses);
				}
				else if(c.getDepartment().equals("Social Studies")) {
					reqs[2]+=courseTracker(c.getCourseID(), studentCourses);	
				}
				else if(c.getDepartment().equals("Science")) {
					reqs[3]+=courseTracker(c.getCourseID(), studentCourses);	
				}
				else if(c.getDepartment().equals("Physical Education") || c.getDepartment().equals("Health")) {
					reqs[0]+=courseTracker(c.getCourseID(), studentCourses);	
				}		
			}
			if(reqs[0]<2) {
				missingReq+="English Credits\n";
			}
			if(reqs[1]<2) {
				missingReq+="Math Credits\n";
			}
			if(reqs[2]<2) {
				missingReq+="Social Studies Credits\n";
			}
			if(reqs[3]<2) {
				missingReq+="Science Credits\n";
			}
			//if(reqs[4]<1) {
			//	missingReq+="PE or Health Credits\n";
			//}
			
			if(missingReq.length()>2) {
				missingReq = s.getInfo() + "\n" + missingReq;
				System.out.println(missingReq);
				results = results + missingReq + "\n";
			}
			
			missingReq="";
		}

        return ResponseEntity.ok(results);
    }

    @GetMapping
    public ResponseEntity<String> reqFailed(){
        ArrayList<Student> sData = new ArrayList<Student>();
        ArrayList<Courses> courseList = new ArrayList<Courses>();
        ArrayList<Requirements> reqList = new ArrayList<Requirements>();

        String missingReq = "", results="";
		int gd;
		
        sData = dataService.getStudentData();
        courseList = dataService.getCourseData();
        reqList = dataService.getReqData();

		ArrayList<Courses> studentCourses;

        for(Student s: sData) {
			gd = s.getGrade();



    	//finds if class requirement exists in student's list
	public static boolean findCourse(String cID, ArrayList<Courses> studentC) {
		int i=0;
		for(Courses c: studentC) {
			if(c.getCourseID().equals(cID)) {
				studentC.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public static int courseTracker(String cID, ArrayList<Courses> studentC) {
		if(findCourse(cID, studentC)){
			return 1;
		}
		
		return 0;
	}

}

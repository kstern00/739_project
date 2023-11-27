package full_stack.demo;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping(value = "/form")
public class MyController {

    private final DataService dataService;
	private final static String courseNames [] = {"English", "Math", "Social Studies", "Science", "Physical Education", "Health"};



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
		//ArrayList<ReqFinder> courseFinder = new ArrayList<>();
		//ArrayList<ReqFinder> reqListFinder = new ArrayList<>();


        String missingReq = "", results="";
		int gd, count = 0;
		
        sData = dataService.getStudentData();
        courseList = dataService.getCourseData();
        reqList = dataService.getReqData();

		ArrayList<Serializable> cList = new ArrayList<>();
		for(Courses c: courseList){
			cList.add(c);
		}

		ArrayList<Serializable> rList = new ArrayList<>();
		for(Requirements r: reqList){
			rList.add(r);
		}


		ArrayList<Courses> studentCourses;

        for(Student s: sData) {
			gd = s.getGrade();
			int[] reqs = {0,0,0,0,0};
			studentCourses = new ArrayList<Courses>(s.getCourses());

			reqs=findRequirement(cList, reqs, studentCourses);
			reqs=findRequirement(rList, reqs, studentCourses);

			for(int i = 0; i<5; i++){
				if(reqs[0]<2) {
					missingReq+=courseNames[i] + "\n";
				}
			}

			
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

        String results="", failedReq="";
		boolean failFlag = false;


		sData = dataService.getStudentData();
        courseList = dataService.getCourseData();
        reqList = dataService.getReqData();

		ArrayList<Serializable> cList = new ArrayList<>();
		for(Courses c: courseList){
			cList.add(c);
		}

		ArrayList<Serializable> rList = new ArrayList<>();
		for(Requirements r: reqList){
			rList.add(r);
		}

		ArrayList<Courses> studentCourses;

        for(Student s: sData) {

			
			studentCourses = new ArrayList<Courses>(s.getCourses());
			for(Courses c: studentCourses){
				if(c.getCourseGrade().equals("F")){

					int[] reqsC = {0,0,0,0,0};
					int[] reqsR = {0,0,0,0,0};

					reqsC=findRequirement(cList, reqsC, studentCourses);
					reqsR=findRequirement(rList, reqsR, studentCourses);

					if(sumArray(reqsC)>0 ||sumArray(reqsR)>0 ){
						failFlag = true;
					}

					if(failFlag){
						failedReq+="Course: " + c.getTitle() + " Course ID: " + c.getCourseID() + "\n";
						failedReq= s.getInfo() + "\n" + failedReq;
						//System.out.println(missingReq);
						results = results + failedReq + "\n";
					}
					failFlag = false;
					failedReq="";
					
				}
			}
		}

			return ResponseEntity.ok(results);
	}


	public static int[] findRequirement(ArrayList<Serializable> obj, int[] req, ArrayList<Courses> studentCourses) {
		ReqFinder reqFinder = null; 
	
		for (Serializable o : obj) {
			if (o instanceof Courses) {
				reqFinder = (Courses) o; // Cast only once for Courses
	
			} else if (o instanceof Requirements) {
				reqFinder = (Requirements) o; // Cast only once for Requirements
			}
	
			if (reqFinder != null) { // Check if reqFinder is not null
				for (int i = 0; i < 5; i++) {
					if (reqFinder.getDepartment().equals(courseNames[i])) {
						req[i] += courseTracker(reqFinder.getCourseID(), studentCourses);
					}
				}
			}
		}
	
		return req;
	}
	


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

	public static int sumArray(int [] arr)
	{
		int sum = 0;
		for(int i = 0; i<5; i++){
			sum+=arr[i];
		}

		return sum;
	}

}

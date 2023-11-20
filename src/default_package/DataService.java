package full_stack.demo;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;

@Service
public class DataService {

	private ArrayList<Student> sData = new ArrayList<Student>();
	private ArrayList<Courses> courseList = new ArrayList<Courses>();
	private ArrayList<Requirements> reqList = new ArrayList<Requirements>();

    @PostConstruct
    public void initData(){
        String line="";
		Scanner fileScan;
		String sString[], cID;
		double credits;
		int grade;
		
		Requirements listReq;
		
		fileScan = new Scanner(new File("gradeReq.txt"));

		//add in grade requirement data
		while (fileScan.hasNext())
		{
			line = fileScan.nextLine();
			sString = line.split(",");
			
			listReq = new Requirements();
			
			try {
				grade = Integer.parseInt(sString[1]);
			}catch (Exception ignore){
				grade=0;
			} //ignores error, assigns grade 0
			
			listReq.Courses(sString[3], sString[2], grade, sString[0]);
			reqList.add(listReq);

		}

        fileScan.close();
		
		Courses listCourse;
		
		fileScan = new Scanner(new File("requirementsList.txt"));

		//add in all courses
		while (fileScan.hasNext())
		{
			listCourse = new Courses();
			line = fileScan.nextLine();
			sString = line.split(",");
			
			try {
				credits = Double.parseDouble(sString[4]);
			}catch (Exception ignore){
				credits = -10.0;
			} //assigns -10.0 so it is noticed
			
			cID = sString[2];
			if(cID.length()<4) {
				for(int j = cID.length(); j<4; j++)
					cID = "0" + cID;
			}
			listCourse.Courses(cID, sString[1], credits, sString[3]);
			courseList.add(listCourse);
			//StdOut.print(listCourse.toString());

		}

		fileScan.close();

		line = "";
		fileScan = new Scanner(new File("students.txt"));

		// Read and process each line of the file
		while (fileScan.hasNext())
		{
			line = line + fileScan.nextLine() + "\n";

		}

		fileScan.close();

		//add in student data
		Student student = new Student();
		String[] studentData = line.split("\n");
				
		
		long tempID, pId = 0;
		int tGrade;
		boolean flag=true;
		String tempS[];
		for (String s: studentData) {
			tempS = s.split(",");
			try {
				tempID = Long.parseLong(tempS[0]);
				tGrade = Integer.parseInt(tempS[3]);
			}
			catch (Exception ignore){
				System.out.println("Problem with data. \n" + s);
				continue;
			} //skips line
			
			if(flag) { //first time through only
				pId = tempID;
				student.Student(tempS[2], tempS[1], tempID, tGrade);
				flag=false;
			}
			
			if(tempID != pId) {
				sData.add(student); //only adds student if it has been through loop already
				student = new Student();
				pId = tempID;
				student.Student(tempS[2], tempS[1], tempID, tGrade);
			}
			
			try {
				credits = Double.parseDouble(tempS[10]);
			}catch (Exception ignore){
				credits = 0.0;
			} //assigns 0.0
			
			//some courses come in as single digit values since the formatting is off
			cID = tempS[7];
			if(cID.length()<4) {
				for(int j = cID.length(); j<4; j++)
					cID = "0" + cID;
			}
			student.setCourses(cID,tempS[8], credits, tempS[9]);

			
		}
		//add last student
		sData.add(student);

    }

    public ArrayList<Student> getStudentData(){
        return sData;
    }

    public ArrayList<Courses> getCourseData(){
        return courseList;
    }

    public ArrayList<Requirements> getReqData(){
        return reqList;
    }

    
}

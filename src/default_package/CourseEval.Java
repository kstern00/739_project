

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;  

import java.util.*;

public class CourseEval {
	

	
	public static void main(String[] args) throws IOException
	{
		String line="";
		Scanner fileScan;
		String sString[], cID;
		double credits;
		int grade;
		
		ArrayList<Requirements> reqList = new ArrayList<Requirements>();
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
			//StdOut.print(listReq.toString());
			
			

		}

		fileScan.close();
		
		
		ArrayList<Courses> courseList = new ArrayList<Courses>();
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
		ArrayList<Student> sData = new ArrayList<Student>();
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
				StdOut.println("Problem with data. \n" + s);
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
			student.setCourses(cID,tempS[8], credits);

			
		}
		//add last student
		sData.add(student);
		
		String missingReq = "", results="";
		int gd;
		
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
				StdOut.println(missingReq);
				results = results + missingReq + "\n";
			}
			
			missingReq="";
		}
		
		try {
			PrintWriter outwriter = new PrintWriter("results.txt", "UTF-8");
		    outwriter.println(results);
			outwriter.close();
		}
		catch(IOException ex) {
			System.out.print("Bad file name");
		}
		


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
}

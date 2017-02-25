package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



import entity.Student;

public class StudentDaoSerializeImpl implements StudentDao {

	@Override
	public boolean isEntityExists(String name) {
		File studentFile = getFile(name);
		return studentFile.exists();
	}


	@Override
	public void saveOrUpdateEntity(Student student) {
			File studentFile = getFile(student.getName());
			ObjectOutputStream output;
			try {
				output = new ObjectOutputStream(new FileOutputStream(studentFile));
				output.writeObject(student);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private File getFile(String name){
		File userFile = new File(getDir(), name);
		return userFile;
		
	}
	private File getDir(){
		File dir = new File("d:/student-dir");
		if(dir.exists()){
			dir.mkdirs();
		}
			return dir;
		
	}
	public List<Student> studentList(){
		File[] studentFiles = getDir().listFiles();
		List<Student> studentList = new ArrayList<>();
		for (File file : studentFiles){
			try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))){
				Student student = (Student) input.readObject();
			}catch(IOException | ClassNotFoundException e){
				 
			}
		}
		return studentList;
	}


	@Override
	public List<Student> listStudent() {
		// TODO Auto-generated method stub
		return null;
	}
}

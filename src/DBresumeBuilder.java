import java.util.ArrayList;

public class DBresumeBuilder {
	DBconnect connection;
	
	public DBresumeBuilder() {
		connection = new DBconnect("resume_db");
	}
	
	public String addResume(Resume resume) {
		String id = Long.toHexString(Double.doubleToLongBits(Math.random()));
		
		connection.addRecord("resumes", "email", resume.getEmail());
		connection.updateRecord("resumes", "user_name", "email", resume.getEmail() , resume.getName());
		connection.updateRecord("resumes", "pass", "email", resume.getEmail() , resume.getPass());
		connection.updateRecord("resumes", "id", "email", resume.getEmail() , id);
		
		for (Edu edu : resume.getEdu()) {
			ArrayList<String> toAdd = new ArrayList<>();
			ArrayList<String> columns = new ArrayList<>();
			//build arrays
			columns.add("resume_id"); 	columns.add("school"); 		columns.add("degree");
			toAdd.add(id); 				toAdd.add(edu.getSchool()); toAdd.add(edu.getDegree());
			//add records
			connection.addRecord("edu", columns, toAdd);
		}
		
		for (Work work : resume.getWork()) {
			String workId = Long.toHexString(Double.doubleToLongBits(Math.random()));
			ArrayList<String> toAdd = new ArrayList<>();
			ArrayList<String> columns = new ArrayList<>();
			//build arrays
			columns.add("id");	columns.add("resume_id"); 	columns.add("company"); 		columns.add("title");
			toAdd.add(workId);  toAdd.add(id); 				toAdd.add(work.getCompany()); 	toAdd.add(work.getTitle());
			//add records
			connection.addRecord("work_xp", columns, toAdd);
			for (String task : work.getTasks()) {
				ArrayList<String> add = new ArrayList<>();
				ArrayList<String> col = new ArrayList<>();
				add.add(workId);		add.add(task);
				col.add("work_xp_id");	col.add("task");
				connection.addRecord("work_tasks", col, add);
			}
		}
		
		for (Skill skill : resume.getskills()) {
			ArrayList<String> toAdd = new ArrayList<>();
			ArrayList<String> columns = new ArrayList<>();
			//build arrays
			columns.add("resume_id"); 	columns.add("skill"); 			columns.add("proficiency");
			toAdd.add(id); 				toAdd.add(skill.getSkill()); 	toAdd.add(skill.getLevel());
			//add records
			connection.addRecord("skills", columns, toAdd);
		}
		return id;
	}
	
	//checks email/pass combo exists in DB, 
	//returns -1 if login fails, returns resume_id otherwise
	public String login(String email,String pass) {
		ArrayList<ArrayList<String>> results = connection.getData("resumes", "email", email, "pass");
		
		if (!results.isEmpty()) {
			if (results.get(0).get(0).equals(pass)) {
				return connection.getData("resumes", "email", email, "id").get(0).get(0);
			} else return "-1";
		} else return "-1";
	}

	public Resume getResume(String id) {
		ArrayList<String> nep = connection.getData("resumes", "id", id, "user_name", "email", "pass").get(0);
		String name = nep.get(0);
		String email = nep.get(1);
		String pass = nep.get(2);
		Resume resume = new Resume(email, pass, name);
		
		//add schools
		ArrayList<ArrayList<String>> DBedu = connection.getData("edu", "resume_id", id, "school", "degree");
		for(ArrayList<String> list : DBedu) {
			resume.addEdu(new Edu(list.get(0), list.get(1)));
		}
		
		//add skills
		ArrayList<ArrayList<String>> DBskill = connection.getData("skills", "resume_id", id, "skill", "proficiency");
		for(ArrayList<String> list : DBskill) {
			resume.addSkill(new Skill(list.get(0), list.get(1)));
		}
		
		//add work
		ArrayList<ArrayList<String>> DBwork = connection.getData("work_xp", "resume_id", id, "company", "title", "id");
		for(ArrayList<String> list : DBwork) {
			ArrayList<String> tasks = new ArrayList<>();
			ArrayList<ArrayList<String>> taskResults = connection.getData("work_tasks", "work_xp_id", list.get(2), "task");
			for (ArrayList<String> t : taskResults) {
				tasks.add(t.get(0));
			}
			resume.addWork(new Work(list.get(0),list.get(1),tasks));
		}
		
		return resume;
	}
	
	public void deleteResume(String id) {
		//delete work tasks
		ArrayList<ArrayList<String>> wrkIds = connection.getData("work_xp", "resume_id", id, "id");
		for (ArrayList<String> wrkId : wrkIds) connection.deleteRecord("work_tasks", "work_xp_id", wrkId.get(0));
		
		//delete edu
		connection.deleteRecord("edu", "resume_id", id);
		
		//delete work
		connection.deleteRecord("work_xp", "resume_id", id);
		
		//delete skills
		connection.deleteRecord("skills", "resume_id", id);
		
		//delete resume
		connection.deleteRecord("resumes", "id", id);
	}
	
	
	
}

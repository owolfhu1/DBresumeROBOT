import java.util.ArrayList;

/**
 *  Created by Orion Wolf_Hubbard on 6/7/2017.
 */
public class Resume {
    private String name;
    private String email;
    private String pass;
    private ArrayList<Edu> edus;
    private ArrayList<Work> works;
    private ArrayList<Skill> skills;


    public Resume(String email, String pass, String name){
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.edus = new ArrayList<>();
        this.works = new ArrayList<>();
        this.skills = new ArrayList<>();
    }

    public String getName(){
        return name;
    }
    
    public String getPass(){
        return pass;
    }

    public String getEmail(){
        return email;
    }

    public ArrayList<Edu> getEdu(){
        return edus;
    }

    public ArrayList<Work> getWork(){
        return works;
    }

    public ArrayList<Skill> getskills(){
        return skills;
    }

    public void addEdu(Edu edu) {
        edus.add(edu);
    }

    public void addWork(Work work) {
        works.add(work);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void removeEdu(int index) {
        edus.remove(index);
    }

    public void removeWork(int index) {
       works.remove(index);
    }

    public void removeSkill(int index){
        skills.remove(index);
    }

    public boolean login(String password) {
        return password.equals(pass);
    }
}
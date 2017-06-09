import java.util.ArrayList;
import java.util.Scanner;

/**
 *  Created by Orion Wolf_Hubbard on 6/7/2017.
 */

public class Prompt {
    private Scanner in = new Scanner(System.in);
    private DBresumeBuilder DBuild = new DBresumeBuilder();
    private String currentResumeId = null;
    private Resume active = null;
    private boolean again = false;
    
    
    //prompts user to edit existing resume or make a new one
    public void promptMainMenu() {
        String next = "";
        while (next.isEmpty()) {
            pf("press enter to continue");
            in.nextLine();
            pf("would you like to: (1)make new resume  (2)edit your resume: ");
            String answer = in.nextLine();
            if (answer.length() > 0) {
                char s = answer.charAt(0);
                switch(s){
                    case '1': next = "new"; break;
                    case '2': next = "edit"; break;
                }
            }
        }
        switch(next){
            case "new": promptUserInfo(); break;
            case "edit": promptEditLogin(); break;
        }
        //if we some how get here..
        promptMainMenu();
    }

    //prompts email and password sends to promptEdit if correct combo
    private void promptEditLogin() {
        String loggedIn = "-1";
        while (loggedIn.equals("-1")) {
            pf("please enter your email: ");
            String email = in.nextLine();
            pf("please enter your password: ");
            String pass = in.nextLine();
            loggedIn = DBuild.login(email, pass);
            if (loggedIn.equals("-1")) pf("incorrect email/password combo, please try again\n");
            else currentResumeId = loggedIn;
        }
        if (!loggedIn.equals("-1")) promptEdit();
        //if somehow gets here, go to main menu
        else promptMainMenu();
    }

    //makes new resume
    public void promptUserInfo() {
    	pf("Building new resume\n");
        pf("enter your name: ");
        String name = in.nextLine();
        pf("enter your email: ");
        String email = in.nextLine();
        pf("enter a password: ");
        String pass = in.nextLine();
        //pileOfResumes.add(0, new Resume(email, pass, name));
        active = new Resume(email, pass, name);
        currentResumeId = "-1";
        promptEdit();
    }


    public void promptEdit() {
    	if (!currentResumeId.equals("-1") && !again) {
    		active = DBuild.getResume(currentResumeId);
    	}
    	again = false;
        String next = "";
        while (next.isEmpty()) {
            pf("press enter to continue");
            in.nextLine();
            pf("would you like to: (1/2)add/remove school  (3/4)add/remove work  (5/6)add/remove skill  (7)exit(prints)  (8)delete resume: ");
            String answer = in.nextLine();
            if (answer.length() > 0) {
                char s = answer.charAt(0);
                switch(s){
                    case '1': next = "school"; break;
                    case '2': next = "rschool"; break;
                    case '3': next = "work"; break;
                    case '4': next = "rwork"; break;
                    case '5': next = "skill"; break;
                    case '6': next = "rskill"; break;
                    case '7': next = "exit"; break;
                    case '8': next = "delete"; break;
                }
            }
        }
        switch(next){
            //delete resume
            case "delete":
            	if (!currentResumeId.equals("-1")) DBuild.deleteResume(currentResumeId);
                active = null;//for good measure
                pf("RESUME DELETED! returning to main menu...");
                promptMainMenu();
                break;
            //remove a school
            case "rschool":
            	if (active.getEdu().size() != 0) {
	                for (int i = 0; i < active.getEdu().size(); i++) pf("%d) %s\n", i + 1, active.getEdu().get(i).getSchool());
	                pf("please enter the school# you would like to delete: ");
	                if (in.hasNextInt()) {
	                    int i = in.nextInt() - 1;
	                    if (i < active.getEdu().size() && i > -1) {
	                        pf("School %s deleted.\n", active.getEdu().get(i).getSchool());
	                        active.removeEdu(i);
	                    }
	                }
            	}
                break;
            //remove a work
            case "rwork":
            	if (active.getWork().size() != 0) {
	                for (int i = 0; i < active.getWork().size(); i++) pf("%d) %s\n", i + 1, active.getWork().get(i).getCompany());
	                pf("please enter the company# you would like to delete: ");
	                if (in.hasNextInt()) {
	                    int i = in.nextInt() - 1;
	                    if (i < active.getWork().size() && i > -1) {
	                        pf("Work %s deleted.\n", active.getWork().get(i).getCompany());
	                        active.removeWork(i);
	                    }
	                }
        		}		
                break;
            //remove a skill
            case "rskill":
            	if (active.getskills().size() != 0) {
	                for (int i = 0; i < active.getskills().size(); i++) pf("%d) %s\n", i + 1, active.getskills().get(i).getSkill());
	                pf("please enter the skill# you would like to delete: ");
	                if (in.hasNextInt()) {
	                    int i = in.nextInt() - 1;
	                    if (i < active.getskills().size() && i > -1) {
	                        pf("Skill %s deleted.\n", active.getskills().get(i).getSkill());
	                        active.removeEdu(i);
	                    }
	                }
            	}
                break;
            //add a school
            case "school":
                pf("School name: ");
                String school = in.nextLine();
                pf("degree: ");
                String degree = in.nextLine();
                active.addEdu(new Edu(school,degree));
                break;
            //add a work
            case "work":
                pf("company name: ");
                String company = in.nextLine();
                pf("title: ");
                String title = in.nextLine();
                ArrayList<String> tempList = new ArrayList<>();
                for (int x = 0; x < 10; x++) {
                    String t;
                    pf("(blank line to exit)enter task: ");
                    t = in.nextLine();
                    if (t.isEmpty()) x = 10;
                    else tempList.add(t);
                }
                active.addWork(new Work(company, title, tempList));
                break;
            //add a skill
            case "skill":
                pf("Skill: ");
                String skill = in.nextLine();
                pf("proficiency: ");
                String level = in.nextLine();
                active.addSkill(new Skill(skill, level));
                break;
        }

        //redirect
        switch(next) {
            case "school": case "work": case "skill": case "rschool": case "rwork": case "rskill": again = true; promptEdit(); break;
            case "exit": Print.console(active); break; //prints and returns to main menu
        }
        
        if (!currentResumeId.equals("-1")) DBuild.deleteResume(currentResumeId);
        currentResumeId = DBuild.addResume(active);
        promptMainMenu();
    }

    //for shorter lines
    private static void pf(String format, Object... args) {
        System.out.printf(format, args);
    }
    
}
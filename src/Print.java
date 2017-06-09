import java.util.ArrayList;

/**
 *  Created by Orion Wolf_Hubbard on 6/8/2017.
 */
public class Print {

    //prints formatted resume to console
    public static void console(Resume resume) {
        ArrayList<Edu> eduList = resume.getEdu();
        ArrayList<Work> workList = resume.getWork();
        ArrayList<Skill> skillList = resume.getskills();

        pad(resume.getName());
        pad(resume.getEmail());
        pad("");
        pad("Education:");
        for(Edu edu : eduList) {
            pad(edu.getSchool());
            pad(edu.getDegree());
            pad("");
        }
        if (!workList.isEmpty()) {
            pad("Work History:");
            for (Work work : workList) {
                pad(work.getTitle());
                pad(work.getCompany());
                ArrayList<String> list = work.getTasks();
                for (String task : list) pad("-%s", task);
                pad("");
            }
        }
        pad("Skills:");
        for(Skill skill : skillList) pad(skill.toString());
    }

    //formats string to like to look like:  "  |  text goes here                                 |"
    //  so that it looks like a page...     "  |  Some more text of dif length                   |"
    //    finaly prints to console..        "  |  last line example...                           |"
    private static void pad(String format, Object... args){ //......................_____________________
        String string = String.format("  |  " + format + "                                                  ", args);
        string = string.substring(0, 50) + "|";
        System.out.println(string);
    }

}
import java.util.ArrayList;

/**
 * Created by Orion Wolf_Hubbard on 6/7/2017.
 */
public class Work {
    private Object[] work= new Object[3];

    public Work(String company, String title, ArrayList<String> tasks) {
        work[0] = company;
        work[1] = title;
        work[2] = tasks;
    }

    public String getCompany() {
        return (String)work[0];
    }

    public String getTitle() {
        return (String)work[1];
    }

    public ArrayList<String> getTasks() {
        return (ArrayList<String>)work[2];
    }

    public void setCompany(String company) {
        work[0] = company;
    }

    public void setTitle(String title) {
        work[1] = title;
    }

    public void setTasks(ArrayList<String> tasks) {
        work[2] = tasks;
    }
}
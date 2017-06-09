/**
 *  Created by Orion Wolf_Hubbard on 6/7/2017.
 */
public class Edu {
    private String[] edu = new String[2];

    public Edu(String school, String degree) {
        edu[0] = school;
        edu[1] = degree;
    }

    public String getSchool() {
        return edu[0];
    }

    public String getDegree() {
        return edu[1];
    }

    public void setSchool(String school) {
        edu[0] = school;
    }

    public void setDegree(String degree) {
        edu[1] = degree;
    }

}
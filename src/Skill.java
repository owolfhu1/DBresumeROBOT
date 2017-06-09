import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Orion Wolf_Hubbard on 6/7/2017.
 */
public class Skill {
    private String[] skill= new String[2];

    public Skill(String skill, String level) {
        this.skill[0] = skill;
        this.skill[1] = level;
    }

    public String getSkill() {
        return skill[0];
    }

    public String getLevel() {
        return skill[1];
    }

    public void setSkill(String skill) {
        this.skill[0] = skill;
    }

    public void setLevel(String level) {
        skill[1] = level;
    }

    @Override
    public String toString(){
        return String.format("-%s: %s", skill[0], skill[1]);
    }

}
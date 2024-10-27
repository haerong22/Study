package reflection;

import reflection.data.Team;
import reflection.data.User;

public class FieldV4 {

    public static void main(String[] args) throws IllegalAccessException {
        User user = new User("id1", null, null);
        Team team = new Team("team1", null);
        System.out.println("===== before =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);

        FieldUtil.nullFieldToDefault(user);
        FieldUtil.nullFieldToDefault(team);

        System.out.println("===== after =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);
    }

    /*
        ===== before =====
        user = User{id='id1', name='null', age=null}
        team = Team{id='team1', name='null'}
        ===== after =====
        user = User{id='id1', name='', age=0}
        team = Team{id='team1', name=''}
     */
}

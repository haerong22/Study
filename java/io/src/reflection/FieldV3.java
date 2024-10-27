package reflection;

import reflection.data.Team;
import reflection.data.User;

public class FieldV3 {

    public static void main(String[] args) {
        User user = new User("id1", null, null);
        Team team = new Team("team1", null);
        System.out.println("===== before =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);

        if (user.getId() == null) {
            user.setId("");
        }

        if (user.getName() == null) {
            user.setName("");
        }

        if (user.getAge() == null) {
            user.setAge(0);
        }

        if (team.getId() == null) {
            team.setId("");
        }

        if (team.getName() == null) {
            team.setName("");
        }

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

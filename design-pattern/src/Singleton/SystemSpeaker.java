package Singleton;

public class SystemSpeaker {

    static private SystemSpeaker instance;
    private int volume;


    private SystemSpeaker() {
        volume = 5;
    }

    public static SystemSpeaker getInstance() {
        if (instance == null) {
            instance = new SystemSpeaker();
            System.out.println("새로 생성");
        } else {
            System.out.println("이미 생성");
        }
        return instance;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}

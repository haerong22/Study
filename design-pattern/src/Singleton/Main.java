package Singleton;

public class Main {

    public static void main(String[] args) {
        SystemSpeaker speaker = SystemSpeaker.getInstance();
        SystemSpeaker speaker2 = SystemSpeaker.getInstance();

        System.out.println(speaker.getVolume());
        System.out.println(speaker2.getVolume());

        speaker.setVolume(11);

        System.out.println(speaker.getVolume());
        System.out.println(speaker2.getVolume());

        speaker2.setVolume(22);
        System.out.println(speaker.getVolume());
        System.out.println(speaker2.getVolume());
    }
}

package poly;

public class TV implements RemoCon{
    @Override
    public void chUp() {
        System.out.println("TV의 채널이 올라간다.");
    }

    @Override
    public void chDown() {
        System.out.println("TV의 채널이 내려간다.");
    }

    @Override
    public void internet() {
        System.out.println("TV는 인터넷 사용 가능");
    }
}

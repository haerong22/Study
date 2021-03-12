package poly;

public class Radio implements RemoCon{
    @Override
    public void chUp() {
        System.out.println("Radio 채널이 올라간다.");
    }

    @Override
    public void chDown() {
        System.out.println("Radio 채널이 내려간다.");
    }

    @Override
    public void internet() {
        System.out.println("Radio는 인터넷 사용 불가");
    }
}

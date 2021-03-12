package poly;

public class TV implements RemoCon{
    int currCH = 95;
    @Override
    public void chUp() {
        if (currCH < RemoCon.MAXCH) {
            currCH++;
        } else {
            currCH = 1;
        }
        System.out.println("TV의 채널이 올라간다. : " + currCH);
    }

    @Override
    public void chDown() {
        if (currCH > RemoCon.MINCH) {
            currCH--;
        } else {
            currCH = RemoCon.MAXCH;
        }
        System.out.println("TV의 채널이 내려간다. : " + currCH);
    }

    @Override
    public void internet() {
        System.out.println("TV는 인터넷 사용 가능");
    }
}

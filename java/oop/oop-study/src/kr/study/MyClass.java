package kr.study;

public class MyClass { // 패키지가 있을 경우 public 을 생략하면 default 접근권한을 가진다.
    public int sum(int a, int b){
        int hap=0;
        for(int i=a;i<=b;i++){
            hap+=i;
        }
        return hap;
    }
}

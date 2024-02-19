package kr.study;

public class IntArray {
    private int count; // 배열의 현재 인덱스
    private int[] arr; // 배열 생성

    // 생성자
    public IntArray(){
        this(10); // 입력값이 없으면 길이 10
    }
    public IntArray(int init){
        arr=new int[init]; // 입력한 값의 길이를 생성
    }

    // 배열에 데이터 추가
    public void add(int data){
        arr[count++]=data;
    }

    // 배열의 값 반환
    public int get(int index){
        return arr[index];
    }

    // 배열의 길이 출력
    public int size(){
        return count;
    }
}

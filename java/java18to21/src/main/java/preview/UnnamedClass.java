
/**
 * Unnamed Class
 * - 패키지, 클래스 선언 X
 * - 다른 클래스에서 접근 불가
 *   => 애플리케이션 시작 지점만 가능
 *
 *  main 함수 결정 순서
 *  1. static void main(String[] args)
 *  2. static void main()
 *  3. void main(String[] args)
 *  4. void main()
 */

String greeting = "Hello World!!";

void main() {
    System.out.println(greeting);
}

void main(String[] args) {

}

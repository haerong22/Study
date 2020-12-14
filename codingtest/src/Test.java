

public class Test {

    public static void main(String[] args) {
        System.out.println(solution("ABABAAAAABA"));
    }

    public static int solution(String name) {
        int len = name.length();
        int move = len-1;
        int answer = 0 ;

        for(int i=0; i<len; ++i) {
            answer += Math.min(name.charAt(i) - 'A', 91 - name.charAt(i));

            int next = i+1;
            while(next<len && name.charAt(next)=='A') {
                ++next;
            }
            move = Math.min(move,i+len-next+Math.min(i,len-next));
        }
        answer += move;

        return answer;
    }
}

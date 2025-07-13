package nio;

import java.nio.IntBuffer;

public class _01_Buffer {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(100);
        System.out.println(buffer.position(0));

        for (int i = 1; i < 51; i++) {
            buffer.put(i * 10);
        }

        System.out.println("Position: " + buffer.position());

        System.out.println(buffer.get(0));
        System.out.println(buffer.get(49));
        System.out.println(buffer.position(25));
        System.out.println(buffer.get(0));
    }

    /*
        java.nio.HeapIntBuffer[pos=0 lim=100 cap=100]
        Position: 50
        10
        500
        java.nio.HeapIntBuffer[pos=25 lim=100 cap=100]
        10
     */
}

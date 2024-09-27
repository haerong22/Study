package thread.control;

import thread.start.HelloRunnable;

import static util.MyLogger.log;

public class ThreadInfoMain {

    public static void main(String[] args) {

        // main 쓰레드
        Thread mainThread = Thread.currentThread();
        log("mainThread = " + mainThread);
        log("mainThread.threadId() = " + mainThread.threadId()); // 중복 X
        log("mainThread.getName() = " + mainThread.getName()); // 중복 가능
        log("mainThread.getPriority() = " + mainThread.getPriority()); // 기본 5, 1(낮음) ~ 10(높음)
        log("mainThread.getThreadGroup() = " + mainThread.getThreadGroup());
        log("mainThread.getState() = " + mainThread.getState()); // NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
        /*
            2024-09-27 01:16:05.852 [     main] mainThread = Thread[#1,main,5,main]
            2024-09-27 01:16:05.856 [     main] mainThread.threadId() = 1
            2024-09-27 01:16:05.856 [     main] mainThread.getName() = main
            2024-09-27 01:16:05.860 [     main] mainThread.getPriority() = 5
            2024-09-27 01:16:05.860 [     main] mainThread.getThreadGroup() = java.lang.ThreadGroup[name=main,maxpri=10]
            2024-09-27 01:16:05.860 [     main] mainThread.getState() = RUNNABLE
         */

        // myThread 쓰레드
        Thread myThread = new Thread(new HelloRunnable(), "myThread");
        log("myThread = " + myThread);
        log("myThread.threadId() = " + myThread.threadId());
        log("myThread.getName() = " + myThread.getName());
        log("myThread.getPriority() = " + myThread.getPriority());
        log("myThread.getThreadGroup() = " + myThread.getThreadGroup());
        log("myThread.getState() = " + myThread.getState());
        /*
            2024-09-27 01:19:11.616 [     main] myThread = Thread[#21,myThread,5,main]
            2024-09-27 01:19:11.616 [     main] myThread.threadId() = 21
            2024-09-27 01:19:11.616 [     main] myThread.getName() = myThread
            2024-09-27 01:19:11.616 [     main] myThread.getPriority() = 5
            2024-09-27 01:19:11.616 [     main] myThread.getThreadGroup() = java.lang.ThreadGroup[name=main,maxpri=10]
            2024-09-27 01:19:11.617 [     main] myThread.getState() = NEW
         */
    }

}

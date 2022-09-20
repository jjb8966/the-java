package not_java8.thread;

import not_java8.thread.code.Task;
import org.junit.jupiter.api.Test;

public class ThreadTest {

    @Test
    void singleThread() throws InterruptedException {
        Task task3 = new Task(0, 1_000_000_000);
        Thread thread3 = new Thread(task3);

        long start = System.currentTimeMillis();
        thread3.start();
        thread3.join();
        long end = System.currentTimeMillis();

        System.out.println("time = " + (end - start));
        System.out.println("result = " + task3.getSum());
    }

    @Test
    void multiThread() throws InterruptedException {
        Task task1 = new Task(0, 500_000_000);
        Task task2 = new Task(500_000_001, 1_000_000_000);

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        long start = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        long end = System.currentTimeMillis();

        System.out.println("time = " + (end - start));
        System.out.println("result = " + (task1.getSum() + task2.getSum()));
    }

    @Test
    void demonThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    Thread.sleep(1000);
                    System.out.println("데몬 쓰레드 실행 중 : " + i + "번째");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread.setDaemon(true);
        thread.start();

        Thread.sleep(3000);
        System.out.println("메인 쓰레드 종료");
    }
}

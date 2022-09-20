package not_java8.thread.code;

public class Task implements Runnable{
    private int start;
    private int end;
    private long sum;

    public Task(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                sum += i;
            }
        }
    }

    public long getSum() {
        return sum;
    }
}

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {
    private static final int number_philosophers = 5;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[number_philosophers];
        Object[] forks = IntStream.range(0, number_philosophers).mapToObj(i -> new Object()).toArray();

        ExecutorService executor = Executors.newFixedThreadPool(number_philosophers);
        IntStream.range(0, number_philosophers).forEach(i -> executor.submit(() -> {
            Object leftFork = forks[i];
            Object rightFork = forks[(i+1) % number_philosophers];

            philosophers[i] = (i == number_philosophers-1)
                    ? new Philosopher(rightFork, leftFork, (i+1))
                    : new Philosopher(leftFork, rightFork, (i+1));
            philosophers[i].start();
        }));
    }
}

class Philosopher extends Thread{

    private final Object left;
    private final Object right;

    Philosopher(Object left, Object right, int num) {
        this.left = left;
        this.right = right;
        this.setName("Philosopher " + num);
    }

    public void run() {
        try {
            while (true) {
                act(" is thinking");
                synchronized (left) {
                    act(" picked up the left fork");
                    synchronized (right) {
                        act(" picked up the right fork and is eating");
                    }
                    act(" put down both forks, stopped eating and is thinking");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void act(String msg) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + msg);
        Thread.sleep(((int) (Math.random()*100)+50));
    }
}
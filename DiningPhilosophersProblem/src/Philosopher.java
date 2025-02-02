public class Philosopher extends Thread{

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
        Thread.sleep(((int) (Math.random()*500)+50));
    }
}

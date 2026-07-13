import java.util.function.IntConsumer;

class FizzBuzz {
    private int n;
    public int i;
    public final Object lock;

    public FizzBuzz(int n) {
        this.n = n;
        this.i = 1;
        lock = new Object(); // you can use 4 locks in order to optmize further
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {

        while (true) {
            synchronized (lock) {

                while (i <= n && !(i % 3 == 0 && i % 5 != 0)) {
                    lock.wait();
                }

                if (i > n) {
                    lock.notifyAll();
                    return;
                }

                printFizz.run();
                i++;
                lock.notifyAll();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {

        while (true) {
            synchronized (lock) {

                while (i <= n && !(i % 5 == 0 && i % 3 != 0)) {
                    lock.wait();
                }

                if (i > n) {
                    lock.notifyAll();
                    return;
                }

                printBuzz.run();
                i++;
                lock.notifyAll();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {

        while (true) {
            synchronized (lock) {

                while (i <= n && !(i % 3 == 0 && i % 5 == 0)) {
                    lock.wait();
                }

                if (i > n) {
                    lock.notifyAll();
                    return;
                }

                printFizzBuzz.run();
                i++;
                lock.notifyAll();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {

        while (true) {
            synchronized (lock) {

                while (i <= n && !(i % 3 != 0 && i % 5 != 0)) {
                    lock.wait();
                }

                if (i > n) {
                    lock.notifyAll();
                    return;
                }

                printNumber.accept(i);
                i++;
                lock.notifyAll();
            }
        }
    }
}
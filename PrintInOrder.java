/*
 * Suppose we have a class:

public class Foo {
  public void first() { print("first"); }
  public void second() { print("second"); }
  public void third() { print("third"); }
}
The same instance of Foo will be passed to three different threads. 
Thread A will call first(), thread B will call second(), and thread C will call third().
Design a mechanism and modify the program to ensure that second() is executed after first(), and third() is executed after second().

Note:

We do not know how the threads will be scheduled in the operating system, even though the numbers in the input seem to imply the ordering. The input format you see is mainly to ensure our tests' comprehensiveness. 
 */

class Foo {

	public Object lock;
	int val;

	public Foo() {

		lock = new Object();

		val = 1;

	}

	public void first(Runnable printFirst) throws InterruptedException {
		synchronized (lock) {

			printFirst.run();
			val = 2;
			lock.notifyAll();
		}

		// printFirst.run() outputs "first". Do not change or remove this line.
	}

	public void second(Runnable printSecond) throws InterruptedException {
		synchronized (lock) {
			while (val != 2) {

				lock.wait();

			}

			val = 3;

			printSecond.run();

			lock.notifyAll();
		}

		// printSecond.run() outputs "second". Do not change or remove this line.
	}

	public void third(Runnable printThird) throws InterruptedException {

		synchronized (lock) {
			while (val != 3) {
				lock.wait();

			}
			printThird.run();

		}
		// printThird.run() outputs "third". Do not change or remove this line.
	}
}
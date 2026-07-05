
/*
 * 1116. Print Zero Even Odd
 * 
 * 
 You have a function printNumber that can be called with an integer parameter and prints it to the console.
 
 For example, calling printNumber(7) prints 7 to the console.
 You are given an instance of the class ZeroEvenOdd that has three functions: zero, even, and odd. The same instance of ZeroEvenOdd will be passed to three different threads:
 
 Thread A: calls zero() that should only output 0's.
 Thread B: calls even() that should only output even numbers.
 Thread C: calls odd() that should only output odd numbers.
 Modify the given class to output the series "010203040506..." where the length of the series must be 2n.
 
 Implement the ZeroEvenOdd class:
 
 ZeroEvenOdd(int n) Initializes the object with the number n that represents the numbers that should be printed.
 void zero(printNumber) Calls printNumber to output one zero.
 void even(printNumber) Calls printNumber to output one even number.
 void odd(printNumber) Calls printNumber to output one odd number.
 */

import java.util.function.IntConsumer;

class ZeroEvenOdd {
	private int n;

	public final Object lock;
	int state;

	public ZeroEvenOdd(int n) {
		this.n = n;
		state = 0; // initial state
		lock = new Object();

	}

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void zero(IntConsumer printNumber) throws InterruptedException {

		for (int i = 1; i <= n; i++) {

			synchronized (lock) {
				while (state != 0) {
					lock.wait();

				}

				printNumber.accept(0);
				state = (i % 2 == 1) ? 1 : 2;
				lock.notifyAll();

			}

		}

	}

	public void odd(IntConsumer printNumber) throws InterruptedException {

		for (int i = 1; i <= n; i += 2) {

			synchronized (lock) {
				while (state != 1) {

					lock.wait();

				}

				printNumber.accept(i);
				state = 0; 
				lock.notifyAll();

			}

		}

	}

	public void even(IntConsumer printNumber) throws InterruptedException {

		for (int i = 2; i <= n; i += 2) {

			synchronized (lock) {
				while (state != 2) {

					lock.wait();

				}

				printNumber.accept(i);
				state = 0; 
				lock.notifyAll();

			}

		}

	}
}

/*
 * 1116. Print Zero Even Odd(Semaphore implementation)
 * 
 * 
 You have a function printNumber that can be called with an integer parameter and prints it to the console.
 
 For example, calling printNumber(7) prints 7 to the console.
 You are given an instance of the class ZeroEvenOdd that has three functions: zero, even, and oddSema. The same instance of ZeroEvenOdd will be passed to three different threads:
 
 Thread A: calls zero() that should only output 0's.
 Thread B: calls even() that should only output even numbers.
 Thread C: calls odd() that should only output oddSema numbers.
 Modify the given class to output the series "010203040506..." where the length of the series must be 2n.
 
 Implement the ZeroEvenOdd class:
 
 ZeroEvenOdd(int n) Initializes the object with the number n that represents the numbers that should be printed.
 void zero(printNumber) Calls printNumber to output one zero.
 void even(printNumber) Calls printNumber to output one even number.
 void odd(printNumber) Calls printNumber to output one oddSema number.
 */

import java.util.function.IntConsumer;

// semaphore allow multiple thread to get the lock

// using semaphore you can mimic state
// if in initial state you want the lock to be unavailiable you can set semaphore of 0

import java.util.concurrent.Semaphore;

class ZeroEvenOdd {
	private int n;

	public final Semaphore zeroSema, oddSema, evenSema;

	public ZeroEvenOdd(int n) {
		this.n = n;
		zeroSema = new Semaphore(1); // initial state max 1 time print
		oddSema = new Semaphore(0); // by default lock is not availiable
		evenSema = new Semaphore(0);

	}

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void zero(IntConsumer printNumber) throws InterruptedException {

		for (int i = 1; i <= n; i++) {

			zeroSema.acquire();
			printNumber.accept(0);

			int nextState = i % 2 == 1 ? 1 : 2;

			

			if (nextState == 1) {
				// oddSema state
				oddSema.release();
			} else {
				evenSema.release();
			}

		}

	}

	public void odd(IntConsumer printNumber) throws InterruptedException {

		for (int i = 1; i <= n; i += 2) {

			oddSema.acquire();
			printNumber.accept(i);

			zeroSema.release();

		}

	}

	public void even(IntConsumer printNumber) throws InterruptedException {

		for (int i = 2; i <= n; i += 2) {
			evenSema.acquire();
			printNumber.accept(i);

			zeroSema.release();

		}

	}
}
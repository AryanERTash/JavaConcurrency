
// 1115. Print FooBar Alternately

/* 
 * Suppose you are given the following code:

class FooBar {
  public void foo() {
    for (int i = 0; i < n; i++) {
      print("foo");
    }
  }

  public void bar() {
    for (int i = 0; i < n; i++) {
      print("bar");
    }
  }
}
The same instance of FooBar will be passed to two different threads:

thread A will call foo(), while
thread B will call bar().
Modify the given program to output "foobar" n times.
*/

class FooBar {
	private int n;
	private final Object lock = new Object(); // or you can use manual lock instead of synchronized keyword
	private boolean isFoo = true;

	public FooBar(int n) {
		this.n = n;
	}

	public void foo(Runnable printFoo) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			synchronized (lock) {
				while (!isFoo) {
					lock.wait();
				}
				printFoo.run();
				isFoo = false;
				lock.notifyAll();
			}
		}
	}

	public void bar(Runnable printBar) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			synchronized (lock) {
				while (isFoo) {
					lock.wait();
				}
				printBar.run();
				isFoo = true;
				lock.notifyAll();
			}
		}
	}
}
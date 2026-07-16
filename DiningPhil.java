import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {

	public ReentrantLock[] forks;

	public DiningPhilosophers() {
		forks = new ReentrantLock[5];

		for (int i = 0; i < forks.length; i++) {
			forks[i] = new ReentrantLock();

		}

	}

	// call the run() method of any runnable to execute its code
	public void wantsToEat(int philosopher,
			Runnable pickLeftFork,
			Runnable pickRightFork,
			Runnable eat,
			Runnable putLeftFork,
			Runnable putRightFork) throws InterruptedException {

		// totak forks for 5 philospher is 5
		int left = philosopher, right = (philosopher + 1) % 5;

		int smallerIndexFork = Math.min(left, right);
		int largerIndexFork = Math.max(left, right);

		forks[smallerIndexFork].lock();
		forks[largerIndexFork].lock();

		if (philosopher == 4) {
			pickLeftFork.run();
			pickRightFork.run();
		} else {
			pickRightFork.run();
			pickLeftFork.run();
		}

		eat.run();

		putLeftFork.run();
		putRightFork.run();

		forks[largerIndexFork].unlock();
		forks[smallerIndexFork].unlock();
	}

}

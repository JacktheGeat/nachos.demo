package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for
 * synchronization.
 *
 * <p>
 * You must implement this.
 *
 * @see	nachos.threads.Condition
 */
public class Condition2 {
    /**
     * Allocate a new condition variable.
     *
     * @param	conditionLock	the lock associated with this condition
     *				variable. The current thread must hold this
     *				lock whenever it uses <tt>sleep()</tt>,
     *				<tt>wake()</tt>, or <tt>wakeAll()</tt>.
     */
    public Condition2(Lock conditionLock) {
	this.conditionLock = conditionLock;
    }

    /**
     * Atomically release the associated lock and go to sleep on this condition
     * variable until another thread wakes it using <tt>wake()</tt>. The
     * current thread must hold the associated lock. The thread will
     * automatically reacquire the lock before <tt>sleep()</tt> returns.
     */
    public void sleep() {
        /*
         */
	Lib.assertTrue(conditionLock.isHeldByCurrentThread());
    // disable interrupts
    boolean interruptStatus = Machine.interrupt().disable();
    // release the lock
	conditionLock.release();
    

    // add the current thread to a queue
    KThread thread = KThread.currentThread();
    waitQueue.add(thread);

    // block the current thread
    KThread.sleep();

    // enable interrupts (when the thread gets woken up)
    Machine.interrupt().restore(interruptStatus);

    // reacquire the lock
	conditionLock.acquire();
    }

    /**
     * Wake up at most one thread sleeping on this condition variable. The
     * current thread must hold the associated lock.
     */
    public void wake() {
	Lib.assertTrue(conditionLock.isHeldByCurrentThread());
    // disable interrupts
    boolean interruptStatus = Machine.interrupt().disable();
    // release the lock
	conditionLock.release();


    // Only wakes a thread if there is one on the queue
    if (!waitQueue.isEmpty()){
        // remove the next thread from the queue
        KThread nextThread = (KThread) waitQueue.removeFirst();
        // wake the new thread
        nextThread.ready();
    }

    // enable interrupts (when the thread gets woken up)
    Machine.interrupt().restore(interruptStatus);
    // reacquire the lock
	conditionLock.acquire();
    }

    /**
     * Wake up all threads sleeping on this condition variable. The current
     * thread must hold the associated lock.
     */
    public void wakeAll() {
	Lib.assertTrue(conditionLock.isHeldByCurrentThread());

    // disable interrupts
    boolean interruptStatus = Machine.interrupt().disable();
    // release the lock
	conditionLock.release();
   

    // Only wakes a thread if there is one on the queue
    while (!waitQueue.isEmpty()){
        // remove the next thread from the queue
        KThread nextThread = (KThread) waitQueue.removeFirst();
        // wake the new thread
        nextThread.ready();
    }

    // enable interrupts (when the thread gets woken up)
    Machine.interrupt().restore(interruptStatus);
    // reacquire the lock
	conditionLock.acquire();
    }

    private Lock conditionLock;
    private SynchList waitQueue = new SynchList();
}

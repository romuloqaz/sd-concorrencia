package ifpb.edu.br.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferLock implements Buffer {
    private Lock lock = new ReentrantLock(false);
    private Condition canWrite = lock.newCondition();
    private Condition canRead = lock.newCondition();
    private Integer buffer = -1;
    private Boolean busy = true;

    @Override
    public void set(int value) {
        lock.lock();
        try {
            while (!busy) {
                System.out.println("The producer tries to record but the buffer is full");
                canWrite.await();
            }
            buffer = value;
            System.out.println("Producer write: " + buffer);
            busy = false;
            canRead.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get() {
        lock.lock();
        try {
            while (busy) {
                System.out.println("Consumer tries to read. But the buffer is empty.");
                canRead.await();
            }
            busy = true;
            System.out.println("Consumer read: " + buffer);
            canWrite.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return buffer;
    }
}

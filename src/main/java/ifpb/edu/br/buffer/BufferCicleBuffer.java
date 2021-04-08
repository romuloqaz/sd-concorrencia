package ifpb.edu.br.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferCicleBuffer implements Buffer{
    private Lock lock = new ReentrantLock(false);
    private Condition canRead = lock.newCondition();
    private Condition canWrite = lock.newCondition();
    private Integer[] buffer = {-1, -1, -1};
    private Integer Busybuffers = 0;
    private Integer readIndexes = 0;
    private Integer recordIndexes = 0;

    @Override
    public void set(int value) {
        try {
            lock.lock();
            if (Busybuffers.equals(buffer.length)) {
                System.out.println("All buffers full. Producer awaits.\\n");
                canWrite.await();
            }
            buffer[recordIndexes] = value;
            System.out.println("Productor write " + value + " position " + recordIndexes);
            if (recordIndexes == (buffer.length - 1)) recordIndexes = -1;
            recordIndexes++;
            Busybuffers++;
            canRead.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get() {
        Integer valueRead = 0;
        lock.lock();
        try {
            if (Busybuffers.equals(0)) {
                System.out.println("All buffers are empty..\nConsumer awaits.\n”");
                canRead.await();
            }
            valueRead = buffer[readIndexes];
            System.out.println("Consumer read " + valueRead + " position " + readIndexes);
            if (readIndexes == (buffer.length -1)) readIndexes = -1;
            readIndexes++;
            Busybuffers--;
            canWrite.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
        return valueRead;
    }
}

package ifpb.edu.br.consumer;

import ifpb.edu.br.buffer.Buffer;

import java.util.Random;

public class Consumer implements Runnable {

    private final Buffer LocationShared;
    private final Random generator = new Random();

    public Consumer(Buffer buffer) {
        this.LocationShared = buffer;
    }

    @Override
    public void run() {
        int count = 0;
        for (int i = 0; i <= 10; i++ ) {
            try {
                Thread.sleep(generator.nextInt(3000));
                count += LocationShared.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("\n%s%d\n ", "End of Consumer. Count value: ", count );
    }
}

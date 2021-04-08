package ifpb.edu.br;

import ifpb.edu.br.buffer.*;
import ifpb.edu.br.consumer.Consumer;
import ifpb.edu.br.productor.Productor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Buffer buffer = new BufferBlokingQueue();

        try {
            executorService.execute(new Productor(buffer));
            executorService.execute(new Consumer(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}

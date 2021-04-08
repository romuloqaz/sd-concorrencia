package ifpb.edu.br.productor;

import ifpb.edu.br.buffer.Buffer;

import java.util.Random;

public class Productor implements Runnable {

    private final Buffer localizacaoCompartilhada;
    private final Random gerador = new Random();

    public Productor(Buffer buffer) {
        this.localizacaoCompartilhada = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++ ) {
            try {
                Thread.sleep(gerador.nextInt(3000));
                localizacaoCompartilhada.set(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("\n%s\n%s\n", "Producer produces! "," End of Producer. ");
    }
}

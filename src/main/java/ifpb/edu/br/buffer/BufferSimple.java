package ifpb.edu.br.buffer;

public class BufferSimple implements Buffer {
    private Integer buffer = -1;

    @Override
    public void set(int value) {
        System.out.printf("Producer write:: \t%2d\n", value);
        this.buffer = value;
    }

    @Override
    public int get() {
        System.out.printf("Consumer read: \t%2d\n", buffer);
        return buffer;
    }
}

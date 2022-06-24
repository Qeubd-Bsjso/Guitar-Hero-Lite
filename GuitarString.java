import java.util.Random;

public class GuitarString {

    private static final double SAMPLING_RATE = 44100;
    private static final double ENERGY_DECAY = .99999999999995;
    private final RingBuffer buffer;
    private int time = 0;

    public GuitarString (double frequency) {
        int capacity = (int)(SAMPLING_RATE/frequency);
        buffer = new RingBuffer(capacity);
        while (!buffer.isFull()) buffer.enqueue(0);
    }

    public GuitarString (double [] init) {
        buffer = new RingBuffer(init.length);
        for (double i : init) buffer.enqueue(i);
    }

    public void pluck () {
        buffer.randomize();
    }

    public void tic () {
        time ++;
        buffer.enqueue((((buffer.dequeue()) + buffer.peek())/2) * ENERGY_DECAY);
    }

    public double sample () {
        return buffer.peek();
    }

    public int time () {
        return time;
    }

}

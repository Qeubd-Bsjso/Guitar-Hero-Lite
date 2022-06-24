import java.util.Random;

public class RingBuffer {

	private final int capacity;
	private double [] arr;
	private int front;
	private int rear;
	
	public RingBuffer(int capacity) throws IllegalArgumentException {
		if (capacity <= 0) 
			throw new IllegalArgumentException("capacity must be greater than 0");
		this.capacity = capacity + 1;
		this.arr = new double[this.capacity];
		this.front = 0;
		this.rear = 0;
	}

	public int size() {
		return (rear + capacity) % capacity - front;
	}

	public boolean isEmpty() {
		return front == rear;
	}

	public boolean isFull() {
		return (rear + 1)%capacity == front;
	}

	public void enqueue(double val) {
		if (isFull()) 
			throw new RuntimeException("Buffer is already full");
		arr[rear] = val;
		rear = (rear + 1)%capacity;
	}

	public double dequeue() {
		if (isEmpty()) 
			throw new RuntimeException("Buffer is already empty");
		int last = front;
		front = (front+1)%capacity;
		return arr[last];
	}

	public double peek() {
		if (isEmpty()) 
			throw new RuntimeException("Buffer is already empty");
		return arr[front];
	}

	public void randomize () {
		Random random = new Random(System.currentTimeMillis());
		double []newArr = new double[this.capacity];
		for (int i = 0; i < this.capacity; i++)
			newArr[i] = random.nextDouble()-.5;
		this.arr = newArr;
	}

}


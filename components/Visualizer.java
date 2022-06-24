package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class Visualizer extends JLabel {

    private static int AMPLITUDE = 80;
    private final BufferedImage image;
    private final Graphics2D graphics;
    private final int width;
    private final int height;

    private final Queue queue;

    int x, y;
    int [] arr;
    int front = 0;

    public Visualizer (int width, int height) {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        x = 0;
        y = 0;
        graphics.setColor(Color.black);
        graphics.drawLine(0, height/2, width, height/2);
        arr = new int[width];
        queue = new Queue(width);
        while (!queue.isFull()) {
            queue.enqueue(getLine(0));
        }
    }

    private Line getLine(double sample) {
        int y1 = (int) Math.floor(AMPLITUDE * sample);
        Line res = new Line(x, height/2 - y, (x+1)%width, height/2 - y1);
        x = (x + 1) % width;
        y = y1;
        return res;
    }

    public void draw(Line prev, Line line) {
        graphics.setColor(Color.white);
        if (prev.x0 < prev.x1)
            graphics.drawLine(prev.x0, prev.y0, prev.x1, prev.y1);
        graphics.setColor(Color.black);
        graphics.drawLine(0, height/2, width, height/2);
        if (line.x0 < line.x1)
            graphics.drawLine(line.x0, line.y0, line.x1, line.y1);
    }

    public void nextStep(double sample) {
        Line prev = queue.dequeue();
        Line cur = getLine(sample);
        queue.enqueue(cur);
        draw(prev, cur);
        repaint();
    }

    @Override
    public void paintComponent(Graphics gTemp) {
        super.paintComponent(gTemp);
        Graphics2D g = (Graphics2D) gTemp;
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        g.dispose();
    }
}


class Line {
    public int x0, x1, y0, y1;
    public Line(int a, int b, int c, int d) {
        x0 = a;
        y0 = b;
        x1 = c;
        y1 = d;
    }

}
class Queue {

    private final int capacity;
    private Line [] arr;
    private int front;
    private int rear;

    public Queue(int c) {
        capacity = c + 1;
        this.arr = new Line[this.capacity];
        this.front = 0;
        this.rear = 0;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public boolean isFull() {
        return (rear + 1)%capacity == front;
    }

    public void enqueue(Line val) {
        if (isFull())
            throw new RuntimeException("Buffer is already full");
        arr[rear] = val;
        rear = (rear + 1)%capacity;
    }

    public Line dequeue() {
        if (isEmpty())
            throw new RuntimeException("Buffer is already empty");
        int last = front;
        front = (front+1)%capacity;
        return arr[last];
    }

}
package components;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BoardKey {
    private Rectangle rectangle;

    private final Color primary;
    private final Color secondary;
    private Color textColor = Color.black;
    private KeyBoard board;
    private boolean hasBorder = true;

    private final String text;
    private int textPosition = 20;
    private final Set<BoardKey> parents = new HashSet<>();

    public BoardKey(Color p, Color s, char c) {
        primary = p;
        secondary = s;
        text = String.valueOf(c);
    }

    public void setBounds(int x, int y, int w, int h) {
        this.rectangle = new Rectangle(x, y, w, h);
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void setGraphics(KeyBoard g) {
        board = g;
    }

    public void setHasBorder(boolean val) {
        hasBorder = val;
    }

    public void setTextLocation(int val) {
        if (val == 1) textPosition = rectangle.height - 20;
        else textPosition = 20;
    }

    public void draw(boolean s) {
        if (s) board.graphics.setColor(secondary);
        else board.graphics.setColor(primary);
        board.graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        if (hasBorder) {
            board.graphics.setColor(Color.BLACK);
            board.graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        board.graphics.setColor(textColor);
        board.graphics.drawString(text, rectangle.x + rectangle.width/2-5, rectangle.y + textPosition);
    }

    public void repaint() {
        this.board.repaint();
    }

    public void addParent(BoardKey key) {
        this.parents.add(key);
    }

    public Iterable<BoardKey> getParents() {
        return this.parents;
    }

    public void press () {
        draw(true);
        for (BoardKey k : parents)
            k.draw(false);
        repaint();
        Thread t = new Thread(new DelayFunction(this));
        t.start();
    }
}


/**
 * thread for button press animation
 */
class DelayFunction implements Runnable {

    BoardKey key;
    public DelayFunction(BoardKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            key.draw(false);
            for (BoardKey k : key.getParents())
                k.draw(false);
            key.repaint();
        } catch (InterruptedException ignored) {

        }
    }
}
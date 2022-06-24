package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class KeyBoard extends JLabel {

    private final Map<Character, BoardKey> keys = new HashMap<>();
    private final Map<Character, BoardKey> parents = new HashMap<>();
    private final String whiteKeyButtons = "qwertyuiop[zxcvbnm,./ ";
    private final String blackKeyButtons = "245789-=dfgjk;'";
    public final Graphics2D graphics;
    private final BufferedImage image;
    private int width = 1200;
    private int height = 400;


    public KeyBoard (int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        this.width = width;
        this.height = height;

        int [] arr = {0, 2, 3, 5, 6, 7, 9, 10, 12, 13, 14, 16, 17, 19, 20};


        int smallWidth = width/ whiteKeyButtons.length();
        int offSet = (width% whiteKeyButtons.length())/2;
        for (int i = 0; i < whiteKeyButtons.length(); i++) {
            BoardKey key = new BoardKey(Color.white, new Color(0xC0C0C0), whiteKeyButtons.charAt(i));
            key.setBounds(offSet + i*smallWidth, 0, smallWidth-3, height-3);
            key.setTextLocation(1);
            keys.put(whiteKeyButtons.charAt(i), key);
            key.setGraphics(this);
        }

        offSet = offSet + smallWidth/2;

        for (int i = 0; i < blackKeyButtons.length(); i++) {
            BoardKey key = new BoardKey(Color.black, new Color(0x303030), blackKeyButtons.charAt(i));
            key.setBounds(offSet + arr[i]*smallWidth, 0, smallWidth-3, 2 * height/3);
            key.setTextLocation(0);
            key.setTextColor(Color.white);
            key.setHasBorder(false);
            keys.put(blackKeyButtons.charAt(i), key);
            key.setGraphics(this);
        }

        // init parents

        for (int i = 0; i < blackKeyButtons.length(); i++) {
            addParent(whiteKeyButtons.charAt(arr[i]), blackKeyButtons.charAt(i));
            addParent(whiteKeyButtons.charAt(arr[i]+1), blackKeyButtons.charAt(i));
        }
        this.setVisible(true);
        this.repaintAll();
        this.repaint();
    }



    @Override
    public void paintComponent(Graphics gTemp) {
        super.paintComponent(gTemp);
        Graphics2D g = (Graphics2D) gTemp;
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        g.dispose();
    }


    public void repaintAll () {
        for (int i = 0; i < whiteKeyButtons.length(); i++)
            keys.get(whiteKeyButtons.charAt(i)).draw(false);
        for (int i = 0; i < blackKeyButtons.length(); i++)
            keys.get(blackKeyButtons.charAt(i)).draw(false);
    }

    private void addParent(char a, char b) {
        keys.get(a).addParent(keys.get(b));
    }

    public void press (char c) {
        BoardKey key = keys.get(c);
        if (key != null) key.press();
    }
}

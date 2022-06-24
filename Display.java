import components.KeyBoard;
import components.Visualizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class Display extends JFrame implements KeyListener {

    private final Map<Character, GuitarString> strings = new HashMap<>();
    private final String keys = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private final KeyBoard keyBoard;
    private final Visualizer visualizer;

    public Display (int width, int height) {
        this.setSize(width, height+50);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
        this.setFocusTraversalKeysEnabled(false);
        this.setResizable(false);
        this.setLayout(null);

        // create strings
        for (int i = 0; i < keys.length(); i++)
            strings.put(keys.charAt(i), new GuitarString(Math.pow(1.05956, i-24) * 440.0));

        visualizer = new Visualizer(width - 30, height/2 - 2);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(1,15,1,15));
        topPanel.setBounds(0, 0, width, height/2);
        topPanel.add(visualizer, BorderLayout.CENTER);
        topPanel.setVisible(true);
        this.add(topPanel);

        keyBoard = new KeyBoard(width, height/2);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBounds(0, height/2 + 5, width, height/2);
        bottomPanel.add(keyBoard, BorderLayout.CENTER);
        bottomPanel.setVisible(true);
        this.add(bottomPanel);

        this.setVisible(true);
        keyBoard.repaintAll();
    }


    /**
     * This method advances the simulation by one tic
     */
    private void ticAll () {
        for (int i = 0; i < keys.length(); i++)
            strings.get(keys.charAt(i)).tic();
    }


    /**
     * This method returns the total superimposed sample value
     * @return superimposed sample
     */
    private double totalSample () {
        double sample = 0;
        for (int i = 0; i < keys.length(); i++)
            sample += strings.get(keys.charAt(i)).sample();
        return sample;
    }


    /**
     * Play the guitar strings
     */
    public void play () {
        while (true) {
            double sample = totalSample();
            StdAudio.play(sample);
            visualizer.nextStep(sample);
            ticAll();
        }
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        char c = keyEvent.getKeyChar();
        GuitarString guitarString = strings.get(c);
        if (guitarString != null) guitarString.pluck();
        keyBoard.press(c);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

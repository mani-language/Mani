package com.mani.lang.Modules.frame;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.Modules.Module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public final class frame implements Module {

    private static JFrame frame;
    private static CanvasPanel panel;
    private static Graphics2D graphics;
    private static BufferedImage img;
    
    private static double lastKey;

    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("window", new CreateWindow());
        interpreter.addSTD("windowVis", new setVisibility());
        interpreter.addSTD("keyPressed", new keyPressed());
    }

    private static class CreateWindow implements ManiCallable {

        @Override
        public int arity() {
            return -1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
			String title = "";
            double width = 640;
            double height = 480;
            switch (arguments.size()) {
                case 1:
                    if (!(arguments.get(0) instanceof String)) { return "Please make sure argument is a string"; }
                    title = arguments.get(0).toString();
                    break;
                case 2:
                    if (!(arguments.get(0) instanceof Double || arguments.get(1) instanceof Double)) {
                        return "Both arguments must be a number, width and height";
                    }
                    width = (double) arguments.get(0);
                    height = (double) arguments.get(1);
                    break;
                case 3:
                    if (!(arguments.get(0) instanceof String || arguments.get(1) instanceof Double || arguments.get(2) instanceof Double)) {
                        return "First argument must be a String, Other 2 must be a number. Width and height.";
                    }
                    title = arguments.get(0).toString();
                    width = (double) arguments.get(1);
                    height = (double) arguments.get(2);
                    break;
            }
            panel = new CanvasPanel(new Double(width).intValue(), new Double(height).intValue());

            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            return null;
		}   
    }

    private static class setVisibility implements ManiCallable {

        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.size() != 1 || arguments.size() == 1 && !(arguments.get(1) instanceof Boolean)) {
                return "Argument must be a boolean. Use the current visibility status, true if displaying, false if not.";
            }
            frame.setVisible((boolean) arguments.get(0) ? false : true );
            return null;
        }
    }

    private static class keyPressed implements ManiCallable {

        @Override
        public int arity() {
            return 0;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            return lastKey;
        }

    }

    private static class CanvasPanel extends JPanel {
        public CanvasPanel(int width, int height) {
            setPreferredSize(new Dimension(width, height));
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics = img.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            setFocusable(true);
            requestFocus();
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    lastKey = new Double(e.getKeyCode()).intValue();
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    lastKey = -1;
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }
    }
}


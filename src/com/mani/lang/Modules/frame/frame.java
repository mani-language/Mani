package com.mani.lang.Modules.frame;

import com.mani.lang.core.Interpreter;
import com.mani.lang.domain.ManiCallable;
import com.mani.lang.Modules.Module;
import com.mani.lang.main.Std;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

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
        interpreter.addSTD("windowButton", new windowButton());
        interpreter.addSTD("buttonVis", new setButtonVis());
        interpreter.addSTD("keyPressed", new keyPressed());
        interpreter.addSTD("windowRepaint", new windowRepaint());
        interpreter.addSTD("windowPrompt", new windowPrompt());
        interpreter.addSTD("line", intConsumer4Convert(com.mani.lang.Modules.frame.frame::line));
        interpreter.addSTD("oval", intConsumer4Convert(com.mani.lang.Modules.frame.frame::oval));
        interpreter.addSTD("foval", intConsumer4Convert(com.mani.lang.Modules.frame.frame::foval));
        interpreter.addSTD("rect", intConsumer4Convert(com.mani.lang.Modules.frame.frame::rect));
        interpreter.addSTD("frect", intConsumer4Convert(com.mani.lang.Modules.frame.frame::frect));
        interpreter.addSTD("clip", intConsumer4Convert(com.mani.lang.Modules.frame.frame::clip));
        interpreter.addSTD("color", new setColor());
    }

    @FunctionalInterface
    private interface IntConsumer4 {
        void accept(int i1, int i2, int i3, int i4);
    }
    
    private static ManiCallable intConsumer4Convert(IntConsumer4 consumer) {
        return new ManiCallable() {
            @Override
            public int arity() { return 4; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                consumer.accept(((Double) arguments.get(0)).intValue() , ((Double) arguments.get(1)).intValue(), ((Double) arguments.get(2)).intValue(), ((Double) arguments.get(3)).intValue());
                return (double) 0;
            }
        };
    }

    private static class setColor implements ManiCallable {

        @Override
        public int arity() {
            return -1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.size() > 1) {
                int r = Std.DoubleToInt((double) arguments.get(0));
                int g = Std.DoubleToInt((double) arguments.get(1));
                int b = Std.DoubleToInt((double) arguments.get(2));
                graphics.setColor(new Color(r, g, b));
                return null;
            }
            graphics.setColor(new Color(Std.DoubleToInt((double) arguments.get(0))));
            return null;
        }
    }

    private static void line(int x1, int y1, int x2, int y2) { graphics.drawLine(x1, y1, x2, y2); }
    private static void oval(int x, int y, int w, int h) { graphics.fillOval(x, y, w, h); }
    private static void foval(int x, int y, int w, int h) { graphics.fillOval(x, y, w, h); }
    private static void rect(int x, int y, int w, int h) { graphics.drawRect(x, y, w, h); }
    private static void frect(int x, int y, int w, int h) { graphics.fillRect(x, y, w, h); }
    private static void clip(int x, int y, int w, int h) { graphics.setClip(x, y, w, h); }

    private static class CreateWindow implements ManiCallable {

        @Override
        public int arity() {
            return -1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
			String title = "";
            double width = 1000;
            double height = 700;
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

    private static class setButtonVis implements ManiCallable {

        @Override
        public int arity() {
            return 2;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            JButton btn = (JButton) arguments.get(0);
            btn.setVisible((Boolean) arguments.get(1) ? false : true);
            return (Boolean) arguments.get(1) ? false : true;
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

    private static class windowButton implements ManiCallable {

        @Override
        public int arity() {
            return -1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            if (arguments.size() < 1) {
                return "Please provide atleast a button name.";
            }
            JButton b = new JButton(arguments.get(0).toString());
            if (arguments.size() == 5) {
                int x = Std.DoubleToInt((double) arguments.get(1));
                int y = Std.DoubleToInt((double) arguments.get(2));
                int w = Std.DoubleToInt((double) arguments.get(3));
                int h = Std.DoubleToInt((double) arguments.get(4));
                b.setBounds(x, y, w, h);
            }
            panel.add(b);
            return null;
        }

    }

    private static class windowPrompt implements ManiCallable {
        @Override
        public int arity() {
            return 1;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            final String v = JOptionPane.showInputDialog(arguments.get(0).toString());
            return (v == null ? null : v);
        }
    }

    private static class windowRepaint implements ManiCallable {

        @Override
        public int arity() {
            return 0;
        }

        @Override
        public Object call(Interpreter interpreter, List<Object> arguments) {
            panel.invalidate();
            panel.repaint();
            return null;
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

    @Override
    public boolean hasExtensions() {
        return false;
    }

    @Override
    public Object extensions() {
        return null;
    }
}

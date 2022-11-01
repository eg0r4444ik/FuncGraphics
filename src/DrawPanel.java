

import drawers.*;
import functions.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class DrawPanel extends JPanel {
    private int currentX, currentY;
    private final ScreenConverter converter;
    private final Line ox;
    private final Line oy;
    private final Line current;
    private Point lastP;
    private ArrayList<IFunction> functions;
    private ArrayList<Color> colors;
    private Map<String, Double> params;
    private ArrayList<AnyFunctions> anyFunc;
    private JCheckBox cubCheckBox;
    private JCheckBox sinCheckBox;
    private JCheckBox sinCosCheckBox;
    private JCheckBox sinExpCheckBox;
    private JCheckBox expCheckBox;
    private JCheckBox hypCheckBox;
    private JCheckBox speCheckBox;
    private Timer timer;


    public DrawPanel() {
        timer = new Timer(20, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();

        params = new HashMap<>();
        params.put("A", 1.0);
        params.put("B", 1.0);
        params.put("C", 1.0);
        params.put("D", 1.0);
        params.put("W", 1.0);
        params.put("F" , 1.0);
        params.put("A1", 1.0);
        params.put("A2", 1.0);
        params.put("F1", 1.0);
        params.put("F2", 1.0);
        params.put("W1", 1.0);
        params.put("W2", 1.0);
        params.put("C1", 1.0);
        params.put("C2", 1.0);
        params.put("T", 1.0);

        functions = new ArrayList<>();
        anyFunc = new ArrayList<>();

        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.PINK);
        colors.add(Color.BLACK);

        cubCheckBox = new JCheckBox("y = A*x^3 + B*x^2 + C*x + D");
        cubCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(cubCheckBox.isSelected()) {
                    functions.add(new CubicFunction());
                } else{
                    for (IFunction f: functions) {
                        if(f instanceof CubicFunction){
                            functions.remove(f);
                            break;
                        }
                    }
                }
                repaint();
            }
        });
        this.add(cubCheckBox);

        sinCheckBox = new JCheckBox("y = A*sin(W*x + F) + C");
        sinCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(sinCheckBox.isSelected()) {
                    functions.add(new SinFunction());
                } else{
                    for (IFunction f: functions) {
                        if(f instanceof SinFunction){
                            functions.remove(f);
                            break;
                        }
                    }
                }
                repaint();
            }
        });
        this.add(sinCheckBox);

        sinCosCheckBox = new JCheckBox("y = A1*sin(W1*x + F1)*(A2*cos(W2*x + F2)+C2) + C1");
        sinCosCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(sinCosCheckBox.isSelected()) {
                    functions.add(new SinCosFunction());
                } else{
                    for (IFunction f: functions) {
                        if(f instanceof SinCosFunction){
                            functions.remove(f);
                            break;
                        }
                    }
                }
                repaint();
            }
        });
        this.add(sinCosCheckBox);

        sinExpCheckBox = new JCheckBox("y = A*sin(W*x + F)*(e^(-x/T))+C");
        sinExpCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(sinExpCheckBox.isSelected()) {
                    functions.add(new SinEFunction());
                } else{
                    for (IFunction f: functions) {
                        if(f instanceof SinEFunction){
                            functions.remove(f);
                            break;
                        }
                    }
                }
                repaint();
            }
        });
        this.add(sinExpCheckBox);

        expCheckBox = new JCheckBox("y = A* 1/(1+e^(-x)) + C");
        expCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(expCheckBox.isSelected()) {
                    functions.add(new ExpFunction());
                } else{
                    for (IFunction f: functions) {
                        if(f instanceof ExpFunction){
                            functions.remove(f);
                            break;
                        }
                    }
                }
                repaint();
            }
        });
        this.add(expCheckBox);

        hypCheckBox = new JCheckBox("y = A / (B*x+D) + C");
        hypCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(hypCheckBox.isSelected()) {
                    functions.add(new HyperbolaFunction());
                } else{
                    for (IFunction f: functions) {
                        if(f instanceof HyperbolaFunction){
                            functions.remove(f);
                            break;
                        }
                    }
                }
                repaint();
            }
        });
        this.add(hypCheckBox);

        speCheckBox = new JCheckBox("x = A*y^3 + B*y^2 + C*y + D");
        speCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(speCheckBox.isSelected()) {
                    functions.add(new SpecialFunction());
                } else{
                    for (IFunction f: functions) {
                        if(f instanceof SpecialFunction){
                            functions.remove(f);
                            break;
                        }
                    }
                }
                repaint();
            }
        });
        this.add(speCheckBox);

        converter = new ScreenConverter(800,600, -2,2, 4, 4);
        ox = new Line(new RealPoint(-converter.getsWidth(),0), new RealPoint(converter.getsWidth(),0));
        oy = new Line(new RealPoint(0,-converter.getsHeight()), new RealPoint(0,converter.getsHeight()));
        current = new Line(new RealPoint(0,0), new RealPoint(0,0));

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(lastP != null){
                    Point curP = e.getPoint();
                    ScreenPoint delta = new ScreenPoint(curP.x - lastP.x,curP.y - lastP.y);
                    RealPoint deltaR = converter.s2r(delta);
                    converter.setX(deltaR.getX());
                    converter.setY(deltaR.getY());
                    lastP = curP;
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                ScreenPoint sp = new ScreenPoint(e.getX(), e.getY());
//                current.setP2(converter.s2r(sp));
//                repaint();
            }
        });

        this.addMouseWheelListener(e -> {
            int count = e.getWheelRotation();
            double base = count < 0 ? 0.99 : 1.01;
            double coef = 1;
            for (int i = Math.abs(count); i > 0; i--) {
                coef *= base;
            }
            converter.setWidth(converter.getWidth() * coef);
            converter.setHeight(converter.getHeight() * coef);
            repaint();
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastP = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastP = null;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        converter.setsHeight(getHeight());
        converter.setsWidth(getWidth());

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D biG = bi.createGraphics();
        biG.setColor(Color.WHITE);
        biG.fillRect(0, 0, getWidth(), getHeight());

        LineDrawer ld = new GraphicsLineDrawer(biG);
        //LineDrawer ld = new DDALineDrawer(new GraphicsPixelDrawer(biG));
        //LineDrawer ld = new BresenhamLineDrawer(new GraphicsPixelDrawer(biG));
        //LineDrawer ld = new WuLineDrawer(new GraphicsPixelDrawer(biG));

        biG.setColor(Color.BLACK);
        //drawLine(ld,converter,ox);
        //drawLine(ld,converter,oy);
        drawCoord(ld, converter);
        drawMarking(ld, converter, biG);


        for(int i = 0; i < functions.size(); i++){
            biG.setColor(colors.get(i%7));
            if(functions.get(i) instanceof SpecialFunction){
                drawSpecialFunction(ld, converter, functions.get(i), Monolog.params);
            } else {
                drawFunction(ld, converter, functions.get(i), Monolog.params);
            }
        }

        for (int i = 0; i < Monolog.anyFunctions.size(); i++) {
            biG.setColor(colors.get(i%7));
            drawAnyFunction(ld, converter, Monolog.anyFunctions.get(i));
        }

        g2d.drawImage(bi, 0, 0, null);
        biG.dispose();
    }

    private static void drawFunction(LineDrawer ld, ScreenConverter sc, IFunction f, Map<String, Double> params){
        double step = sc.getWidth() / sc.getsWidth();
        double x = sc.getX() + 0 * step;
        double y = f.compute(x, params);
        for(int i = 1; i < sc.getsWidth(); i++) {
            double x1 = sc.getX() + i * step;
            double y1 = f.compute(x1, params);
            if (y1 < sc.getY() && y1 > sc.getY() - sc.getHeight()){
                drawLine(ld, sc, new Line(new RealPoint(x, y), new RealPoint(x1,y1)));
            }
            x = x1;
            y = y1;
        }
    }

    private static void drawSpecialFunction(LineDrawer ld, ScreenConverter sc, IFunction f, Map<String, Double> params){
        double step = sc.getHeight() / sc.getsHeight();
        double y = sc.getY() - 0 * step;
        double x = f.compute(y, params);
        for(int i = 1; i < sc.getsHeight(); i++) {
            double y1 = sc.getY() - i * step;
            double x1 = f.compute(y1, params);
            if (x1 > sc.getX() && x1 < sc.getX() + sc.getWidth()){
                drawLine(ld, sc, new Line(new RealPoint(x, y), new RealPoint(x1,y1)));
            }
            x = x1;
            y = y1;
        }
    }

    private static void drawAnyFunction(LineDrawer ld, ScreenConverter sc, AnyFunctions f){
        if(f.defineArg() == 'x'){
            double step = sc.getHeight() / sc.getsHeight();
            double y = sc.getY() - 0 * step;
            double x = f.compute(y);
            for(int i = 1; i < sc.getsHeight(); i++) {
                double y1 = sc.getY() - i * step;
                double x1 = f.compute(y1);
                if (x1 > sc.getX() && x1 < sc.getX() + sc.getWidth()){
                    drawLine(ld, sc, new Line(new RealPoint(x, y), new RealPoint(x1,y1)));
                }
                x = x1;
                y = y1;
            }
        }
        if(f.defineArg() == 'y'){
            double step = sc.getWidth() / sc.getsWidth();
            double x = sc.getX() + 0 * step;
            double y = f.compute(x);
            for(int i = 1; i < sc.getsWidth(); i++) {
                double x1 = sc.getX() + i * step;
                double y1 = f.compute(x1);
                if (y1 < sc.getY() && y1 > sc.getY() - sc.getHeight()){
                    drawLine(ld, sc, new Line(new RealPoint(x, y), new RealPoint(x1,y1)));
                }
                x = x1;
                y = y1;
            }
        }
    }

    private static void drawCoord(LineDrawer ld, ScreenConverter sc){
        double step = sc.getWidth() / sc.getsWidth();
        double x = sc.getX() + 0 * step;
        double y = 0;
        for(int i = 1; i < sc.getsWidth(); i++) {
            double x1 = sc.getX() + i * step;
            double y1 = 0;
            drawLine(ld, sc, new Line(new RealPoint(x, y), new RealPoint(x1,y1)));
            x = x1;
            y = y1;
        }

        step = sc.getHeight() / sc.getsHeight();
        y = sc.getY() - 0 * step;
        x = 0;
        for(int i = 1; i < sc.getsHeight(); i++) {
            double y1 = sc.getY() - i * step;
            double x1 = 0;
            drawLine(ld, sc, new Line(new RealPoint(x, y), new RealPoint(x1,y1)));
            x = x1;
            y = y1;
        }
    }

    private static void drawMarking(LineDrawer ld,ScreenConverter sc, Graphics2D biG){
        int step = 1;
        if (sc.getWidth() > 1000) {
            int n = 0;
            while(sc.getWidth() > 500*Math.pow(2, n)){
                n++;
            }
            step = 50*(int)Math.pow(2, n-1);
        }else if (sc.getWidth() > 500) {
            step = 50;
        }else if (sc.getWidth() > 200) {
            step = 20;
        }else if (sc.getWidth() > 50) {
            step = 10;
        }else if (sc.getWidth() > 20) {
            step = 5;
        }
        for (int i = 0; i < sc.getWidth(); i+=step) {
            if (i == 0) {
                RealPoint rp = new RealPoint(0.1*step, 0.1*step);
                biG.drawString(String.valueOf(i), (float) sc.r2s(rp).getX(), (float) sc.r2s(rp).getY());
            } else {
                RealPoint rp = new RealPoint(i, 0.1 * step);
                biG.drawString(String.valueOf(i), (float) sc.r2s(rp).getX(), (float) sc.r2s(rp).getY());
                drawLine(ld, sc, new Line(new RealPoint(i, -0.05 * step), new RealPoint(i, 0.05 * step)));
            }
        }
        for (int i = 0; i > -sc.getWidth(); i-=step) {
            if (i == 0) {
                RealPoint rp = new RealPoint(0.1*step, 0.1*step);
                biG.drawString(String.valueOf(i), (float) sc.r2s(rp).getX(), (float) sc.r2s(rp).getY());
            } else {
                RealPoint rp = new RealPoint(i, 0.1 * step);
                biG.drawString(String.valueOf(i), (float) sc.r2s(rp).getX(), (float) sc.r2s(rp).getY());
                drawLine(ld, sc, new Line(new RealPoint(i, -0.05 * step), new RealPoint(i, 0.05 * step)));
            }
        }
        for (int i = 0; i < sc.getHeight(); i+=step) {
            if (i != 0) {
                RealPoint rp = new RealPoint(0.1 * step, i);
                biG.drawString(String.valueOf(i), (float) sc.r2s(rp).getX(), (float) sc.r2s(rp).getY());
                drawLine(ld, sc, new Line(new RealPoint(-0.05 * step, i), new RealPoint(0.05 * step, i)));
            }
        }
        for (int i = 0; i > -sc.getHeight(); i-=step) {
            if (i != 0) {
                RealPoint rp = new RealPoint(0.1 * step, i);
                biG.drawString(String.valueOf(i), (float) sc.r2s(rp).getX(), (float) sc.r2s(rp).getY());
                drawLine(ld, sc, new Line(new RealPoint(-0.05 * step, i), new RealPoint(0.05 * step, i)));
            }
        }
    }


    private static void drawLine(LineDrawer ld, ScreenConverter sc, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public void setParams(Map<String, Double> params) {
        this.params = params;
        repaint();
    }
}

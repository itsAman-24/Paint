import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdvancedPaintApp extends Frame {
    private int prevX, prevY;
    private Color currentColor = Color.BLACK;
    private int brushSize = 5;
    private String currentTool = "Freehand";
    
    public AdvancedPaintApp() {
        setTitle("Advanced Paint Application");
        setSize(600, 500);
        setLayout(new FlowLayout());

        // Color Picker
        Button colorButton = new Button("Change Color");
        colorButton.addActionListener(e -> {
            currentColor = JColorChooser.showDialog(this, "Pick a Color", currentColor);
        });

        // Brush Size Selector
        Choice sizeChoice = new Choice();
        for (int i = 1; i <= 20; i += 2) sizeChoice.add(String.valueOf(i));
        sizeChoice.addItemListener(e -> brushSize = Integer.parseInt(sizeChoice.getSelectedItem()));

        // Shape Selector
        Choice toolChoice = new Choice();
        toolChoice.add("Freehand");
        toolChoice.add("Rectangle");
        toolChoice.add("Circle");
        toolChoice.add("Line");
        toolChoice.addItemListener(e -> currentTool = toolChoice.getSelectedItem());

        add(colorButton);
        add(new Label("Brush Size:"));
        add(sizeChoice);
        add(new Label("Tool:"));
        add(toolChoice);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Graphics g = getGraphics();
                g.setColor(currentColor);

                if (currentTool.equals("Freehand")) {
                    g.fillOval(e.getX(), e.getY(), brushSize, brushSize);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                Graphics g = getGraphics();
                g.setColor(currentColor);

                if (currentTool.equals("Rectangle")) {
                    g.drawRect(Math.min(prevX, e.getX()), Math.min(prevY, e.getY()), 
                               Math.abs(e.getX() - prevX), Math.abs(e.getY() - prevY));
                } else if (currentTool.equals("Circle")) {
                    g.drawOval(Math.min(prevX, e.getX()), Math.min(prevY, e.getY()), 
                               Math.abs(e.getX() - prevX), Math.abs(e.getY() - prevY));
                } else if (currentTool.equals("Line")) {
                    g.drawLine(prevX, prevY, e.getX(), e.getY());
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdvancedPaintApp();
    }
}
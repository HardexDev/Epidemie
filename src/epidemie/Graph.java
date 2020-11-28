/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alexis Robin
 */
public class Graph extends JPanel {
    
    private ArrayList<Double> points;
    private static final long serialVersionUID = 1L;
    private int labelPadding = 12;
    /**change the line color to the best you want;*/
    private Color lineColor = new Color(255,255,254);
    private Color pointColor = new Color(255,0,255 );
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private static int pointWidth = 10;
    private int numberYDivisions = 6;
    private int padding = 20;
    
    public Graph(ArrayList<Double> points){
        this.points = points;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) / (points.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxPoint() - getMinPoint());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxPoint() - points.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        g2.setColor(Color.WHITE);
	    //fill the rect
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (points.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinPoint() + (getMaxPoint() - getMinPoint()) * 
                		((i * 8.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        for (int i = 0; i < points.size(); i++) {
            if (points.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (points.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((points.size() / 8.0)) + 3)) == 0) { 
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

   
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }
    
    private double getMinPoint() {
        double minPoint = Double.MAX_VALUE;
        for (Double point : points) {
            minPoint = Math.min(minPoint, point);
        }
        return minPoint;
    }
    
    private double getMaxPoint() {
        double maxPoint = Double.MIN_VALUE;
        for (Double point : points) {
            maxPoint = Math.max(maxPoint, point);
        }
        return maxPoint;
    }
    
    public void setScores(ArrayList<Double> points) {
        this.points = points;
        invalidate();
        this.repaint();
    }

    public ArrayList<Double> getScores() {
        return points;
    }
    
    public static void createAndShowGUI(){
        EulerExplicite euler = new EulerExplicite();
        ArrayList<Double> scores = euler.getData(1000);
        Graph mainPanel = new Graph(scores);
        mainPanel.setPreferredSize(new Dimension(700, 600));
        JFrame frame = new JFrame("Sample Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}

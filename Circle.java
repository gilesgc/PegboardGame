
/**
 * Object used in a GUI application
 * 
 * Grant Giles
 * 9/18/2018
 */
import java.awt.*;

public class Circle
{
    private int radius, centerX, centerY;
    private Color color;
    
    public Circle(int x, int y, int r, Color c) {
        centerX = x;
        centerY = y;
        radius = r;
        color = c;
    }
    
    public void draw(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.drawOval(centerX - radius, centerY - radius,
                    radius * 2, radius * 2);
        g.setColor(oldColor);
    }
    public void fill(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillOval(centerX - radius, centerY - radius,
                    radius * 2, radius * 2);
        g.setColor(oldColor);
    }
    public void move(int xAmount, int yAmount) {
        centerX += xAmount;
        centerY += yAmount;
    }
    public void setPosition(int x, int y) {
        centerX = x;
        centerY = y;
    }
    public boolean containsPoint(int x, int y) {
        int xSquared = (x - centerX) * (x - centerX);
        int ySquared = (y - centerY) * (y - centerY);
        int radiusSquared = radius * radius;
        
        return radiusSquared >= xSquared + ySquared;
    }
    public void setRadius(int newRadius) {
        radius = newRadius;
    }
    public int getRadius() {
        return radius;
    }
    public int getCenterX() { return centerX; }
    public int getCenterY() { return centerY; }
    public Color getColor() { return color; }
    public void setColor(Color c) { color = c; }
}

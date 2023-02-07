package tracker;

import java.awt.*;
import java.util.*;


public class DangerZones
{

  private ArrayList zoneList;   

  
  private final int radius;
  private final Color color;
  private final Point location;


  public DangerZones(int x, int y, int radius, Color color, int numZones, Graphics g)
  {
      this.location = new Point(x, y);
      this.radius = radius;
      this.color = color;
	System.out.println("Num. danger zones: "+ numZones);     

    zoneList = new ArrayList();

    for (int i=0 ; i < numZones; i++) {

    	draw(g);
    }
  }


  public boolean contains(int x, int y) {
      double distance = Point.distance(x, y, location.x, location.y);
      double radiusD = radius;
      if (radiusD >= distance) {
          return true;
      } else {
          return false;
      }
  }


  public void draw(Graphics g) {
      g.setColor(color);
      g.fillOval(location.x - radius, location.y - radius, radius + radius,
              radius + radius);
  }


} 


package tracker;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.*;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Animal extends JPanel
{

JFrame simFrame;
protected HerdVector position = new HerdVector();
protected HerdVector velocity = new HerdVector();
protected HerdVector newVel = new HerdVector();
static final HerdVector migrate = new HerdVector(0.02, 0);
protected HerdVector randVel = new HerdVector();
protected HerdVector acceleration;

protected final static double PROXIMITY = 2.0; 
protected static final Random r = new Random();
protected ArrayList velChanges = new ArrayList();
static final int size = 9;
public Color color;
final double maxForce, maxSpeed;
private boolean included = true;

protected List<Animal> herdList;

public Animal(double x, double y, Color color)
{
	herdList = new ArrayList<>();
	acceleration = new HerdVector();
	position.set(x, y);
	velocity= new HerdVector(r.nextInt(3) + 1, r.nextInt(3) - 1);
	maxSpeed = 3.0;
	maxForce = 0.05;
	this.color = color;
	
}

void run(Graphics2D g) {
	System.out.println("run of herd list");
    for (Animal a : herdList) {
        a.run(g,herdList);
    }
}

/*
 * Add animals to the arraylist
 */
void addAnimal(Animal b) {
	herdList.add(b);
}
/*
 * create all animals of a herd
 */
public static Animal create(double w, double h, int animalCnt,Color color) {
	Animal herd = new Animal(w, h, color);
    for (int i = 0; i < animalCnt; i++)
    	herd.addAnimal(new Animal(w, h, color));
    return herd;
}
//----------------------------------------------------------------//
/*
 * Claculate the changes in velocity
 */
protected HerdVector calcVelocity()
{	

velChanges.clear(); 

HerdVector v = randVelocity();   
if ((v.x == 0) && (v.y == 0)) 
	velRules();
else
	velChanges.add(v);
  newVel.set( velocity );   
  for(int i=0; i < velChanges.size(); i++)
    newVel.add( (HerdVector)velChanges.get(i) );  
 
  newVel.scale( limitMaxSpeed() );
  return newVel;
} 
/*
 * setting random velocity for normal movement
 */
protected HerdVector randVelocity() {

	randVel.set(0,0);   
	randVel.set(r.nextInt(3) + 1, r.nextInt(3) - 1);
	randVel.scale(0.2);
	return randVel;
}
/*
 * Velocity rules for normal movement
 * override this method to add new velocity rules
 */
protected void velRules()
{
HerdVector v1 = cohesion(herdList);
HerdVector v2 = separation(herdList);
HerdVector v3 = alignment(herdList);
HerdVector v4 = boundPosition(1150,850);

v1.mult(2.5);
v2.mult(1.5);
v3.mult(1.3);

applyForce(v1);
applyForce(v2);
applyForce(v3);


velChanges.add(v1); 
velChanges.add(v2);
velChanges.add(v3); 
velChanges.add(v4); 
} 
/*
 * Bind the movement of the herd to the canvas
 */
public HerdVector boundPosition(int xMax, int yMax) {
    int x = 0;
    int y = 0;
    if (this.position.x < 0)                x = 10;
    else if (this.position.x  > xMax)       x = -10;
    if (this.position.y  < 0)               y = 10;
    else if (this.position.y  > yMax)       y = -10;
    return new HerdVector(x,y);
}


/*
 * update velocity of animals
 */
void update() {
	System.out.println("update velocity of animals");
    velocity.add(acceleration);
    velocity.limit(maxSpeed);
    position.add(velocity);
    acceleration.mult(0);
}
/*
 * Apply acceleration
 */
void applyForce(HerdVector force) {
    acceleration.add(force);
}
/*
 * For directing the herd
 */
HerdVector direct(HerdVector target) {
    HerdVector navigate = HerdVector.sub(target, position);
    navigate.normalize();
    navigate.mult(maxSpeed);
    navigate.sub(velocity);
    navigate.limit(maxForce);
    return navigate;
}

//scale herd speed so no faster than maxSpeed
public double limitMaxSpeed()
{
	double speed = velocity.length();
	if(speed > maxSpeed)
	  return maxSpeed/speed;
  else
    return 1.0;   
}
/*
 * movement of the herd
 */
void move(Graphics2D g, List<Animal> herd) {
    view(g, herd);

    HerdVector rule1 = separation(herd);
    HerdVector rule2 = alignment(herd);
    HerdVector rule3 = cohesion(herd);
    HerdVector rule4 = boundPosition(1150,850);

    rule1.mult(2.5);
    rule2.mult(1.5);
    rule3.mult(1.3);

    applyForce(rule1);
    applyForce(rule2);
    applyForce(rule3);
    applyForce(rule4);
    applyForce(migrate);
}
/*
 * Find the animals in view
 */
void view(Graphics2D g, List<Animal> herds) {
    double sightDistance = 100;
    double peripheryAngle = PI * 0.85;

    for (Animal s : herds) {
        s.included = false;

        if (s == this)
            continue;

        double d = HerdVector.dist(position, s.position);
        if (d <= 0 || d > sightDistance)
            continue;

        HerdVector lineOfSight = HerdVector.sub(s.position, position);

        double angle = HerdVector.angleBetween(lineOfSight, velocity);
        if (angle < peripheryAngle)
            s.included = true;
    }
}

/*
 * For finding the centroid of the herd
 */

public HerdVector centroid(List<Animal> herds)  {
	HerdVector center = new HerdVector();
        int centx=0,centy=0;
        int count =0;
        for(Animal b : herds) {
        centx += b.getX();
        centy += b.getY();
        count++;
        }
    center.x=centx/count;
    center.y=centy/count;
    return center;

}

/*
 * Cohesion: handles flock centering, navigateing the sheep towards the average position of its neighbors. 
 */
HerdVector cohesion(List<Animal> herds) {
    double prefDist = 50;

    HerdVector avgPosition = new HerdVector(0, 0);
    int count = 0;

    for (Animal s : herds) {


        double d = HerdVector.dist(position, s.position);
        if ((d > 0) && (d < prefDist)) {
            avgPosition.add(s.position);
            count++;
        }
    }
    if (count > 0) {
        avgPosition.div(count);
        return direct(avgPosition);
    }
    return avgPosition;
}

/*
 * Separation: navigates the sheep away from neighbors that are too close in order to avoid crowding or collision.
 */

HerdVector separation(List<Animal> herds) {
    double desiredSeparation = 25;

    HerdVector navigate = new HerdVector(0, 0);
    int count = 0;
    for (Animal s : herds) {


        double d = HerdVector.dist(position, s.position);
        if ((d > 0) && (d < desiredSeparation)) {
            HerdVector diff = HerdVector.sub(position, s.position);
            diff.normalize();
            diff.div(d);        
            navigate.add(diff);
            count++;
        }
    }
    if (count > 0) {
        navigate.div(count);
    }

    if (navigate.mag() > 0) {
        navigate.normalize();
        navigate.mult(maxSpeed);
        navigate.sub(velocity);
        navigate.limit(maxForce);
        return navigate;
    }
    return new HerdVector(0, 0);
}

/*
 * Alignment: aligns the sheep’s velocity with the average direction of its neighbours, causing it to head in the same direction. 

 */

HerdVector alignment(List<Animal> herds) {
    double preferredDist = 50;

    HerdVector navigate = new HerdVector(0, 0);
    int count = 0;

    for (Animal s : herds) {


        double d = HerdVector.dist(position, s.position);
        if ((d > 0) && (d < preferredDist)) {
            navigate.add(s.velocity);
            count++;
        }
    }

    if (count > 0) {
        navigate.div(count);
        navigate.normalize();
        navigate.mult(maxSpeed);
        navigate.sub(velocity);
        navigate.limit(maxForce);
    }
    return navigate;
}





//---------------------------------------------------------------//
/*
 * draw each animal
 */

void draw(Graphics2D g, Color color) {
	AffineTransform save = g.getTransform();

    g.translate(position.x, position.y);
    g.rotate(velocity.heading() + PI / 2);
    g.setColor(color);
    g.fillOval(size, size, size, size);


    g.setTransform(save);
}

void run(Graphics2D g, List<Animal> herdList) {
		
    move(g, herdList);
    
    //update(vel,pos);
    update();
    draw(g,color);
}

public HerdVector getPosition() {
	return position;
}

public HerdVector getVelocity() {
	return velocity;
}

public List<Animal> getHerdList() {
	return herdList;
}




} 
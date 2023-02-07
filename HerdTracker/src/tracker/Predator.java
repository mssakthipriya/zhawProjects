package tracker;

import static java.lang.Math.PI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Predator extends Animal
{
	
	private final static double FIND_WEIGHT = 0.2;	
	private Sheep prey;
	private HerdVector preyPosition = new HerdVector();
	private HerdVector distance = new HerdVector();

static List<Predator> predatorList = new ArrayList<>();
List<Sheep> sList= new ArrayList<>();

public Predator(double x, double y, int num,Color color) {
	super(x, y, color);
	// TODO Auto-generated constructor stub
	//predatorList = new ArrayList<>();
}


public static Predator create(double w, double h, int animalCnt,Color color) {
	System.out.println("animalCnt" + animalCnt );
	Predator predator = new Predator(w, h,animalCnt, color);
	predatorList = new ArrayList<>();
    for (int i = 0; i < animalCnt; i++) {
    	predator.addAnimal(new Predator(w, h,animalCnt, color));
    	predatorList.add(new Predator(w, h,animalCnt,color)); 	
    	
    }
    System.out.println("final size of predators:"+predatorList.size());
    
	return predator;

}

/*
 * Detect prey by predator
 */
public HerdVector detectPrey(HerdVector position)

{ System.out.println("Entered detect prey");
	prey = new Sheep(0, 0, 0, color.BLUE);
	sList = prey.getSheepList();
	   
  int numClosePrey = 0;
  preyPosition.set(0,0);
  HerdVector pos;
  Animal p;
 
  System.out.println("sheep size:"+sList.size());
  for(int i = 0; i < sList.size();i ++)
  {
	  p = sList.get(i);
	    pos = p.getPosition();
	    distance.set(pos);
	    System.out.println("postion : "+position+" : pos :"+pos);
	    distance.sub(position,pos);
	    System.out.println("length of distance : "+distance.length()+" : proximity :"+(PROXIMITY*1.5));
		  if(distance.length() < PROXIMITY*1.5) {
			  System.out.println("sheep in proximity");
			  preyPosition.add( distance );	 
	      numClosePrey++;
	    } 
  }

	if (numClosePrey > 0) {
		preyPosition.scale(1.0f/numClosePrey);  
		preyPosition.scale(FIND_WEIGHT);
  }
  return preyPosition;
}

protected void velRules()
{System.out.println("inside velocity rule of predator");
 HerdVector v = (detectPrey(position));
 velChanges.add(v);

super.velRules();
} 

protected HerdVector calcVelocity()
{	
velChanges.clear(); 
System.out.println("inside calc velocity: prey::"+sList.size());
HerdVector v = randVelocity();   
if (sList.equals(null)) 
	velChanges.add(v);	
else
	velRules();
  newVel.set( velocity );   
  for(int i=0; i < velChanges.size(); i++)
    newVel.add( (HerdVector)velChanges.get(i) );  
 
  newVel.scale( limitMaxSpeed() );
  return newVel;
} 



void update() {
	System.out.println("update velocity of pred");
    velocity.set(calcVelocity());
    velocity.add(acceleration);
    velocity.limit(maxSpeed);
    position.add(velocity);
    acceleration.mult(0);
}

public void setPrey(Sheep prey) {
	this.prey = prey;
}

public List<Predator> getPredatorList()
{  return predatorList;  }

} 
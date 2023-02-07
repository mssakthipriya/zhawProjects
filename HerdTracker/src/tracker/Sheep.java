package tracker;

import static java.lang.Math.PI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class Sheep extends Animal
{
	  private HerdVector avoidPred = new HerdVector();
	  private HerdVector distFrom = new HerdVector();
	  private Predator pred;

	List<Predator> pList= new ArrayList<>();
	static List<Sheep> preyList= new ArrayList<>();
	private final static double FLEE_VALUE = 0.2;




public Sheep(double x, double y, int num, Color color) {
	super(x, y, color);
	// TODO Auto-generated constructor stub
	
	

}

public static Sheep create(double w, double h, int animalCnt,Color color) {
	System.out.println("animalCnt" + animalCnt );	
	Sheep sheep = new Sheep(w, h,animalCnt, color);
	preyList = new ArrayList<>();
    for (int i = 0; i < animalCnt; i++) {
    	sheep.addAnimal(new Sheep(w, h,animalCnt, color));
    	preyList.add(new Sheep(w, h,animalCnt, color));
    }
    System.out.println("fresh size of sheeps:"+preyList.size());
    return sheep;
}


/*
 * Detect predator by sheep
 */
public HerdVector detectPredators(HerdVector position)
{
	pred = new Predator(0, 0, 0, color.RED);
	pList = pred.getPredatorList();
	System.out.println("fresh size of sheep:"+preyList.size());
	System.out.println("predator size:"+pList.size());
	//predatorList = pred.getPredatorList();  
avoidPred.set(0,0);  // reset
HerdVector predPos;
Animal b;

System.out.println("herd size:"+herdList.size());
for(int i = 0; i < pList.size();i ++)
{
	b = pList.get(i);
  distFrom.set(position);
  predPos = b.getPosition();
  distFrom.sub(predPos);
  if(distFrom.length() < PROXIMITY) {   
    avoidPred.set(distFrom);
    avoidPred.scale(FLEE_VALUE);
  }else {
	  avoidPred= randVelocity(); 
  }
}
return avoidPred;
} 


protected void velRules()
{ 
	System.out.println("inside velocity rule of prey");
  HerdVector v = detectPredators(position);
  velChanges.add(v);
  super.velRules();
}

protected HerdVector calcVelocity()
{	
	velChanges.clear(); 
	System.out.println("inside calc velocity: pred::"+pList.size());
	HerdVector v = randVelocity();   
	if (pList.equals(null)) 
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
	System.out.println("update velocity of sheep");
	
    velocity.set(calcVelocity());    
    velocity.add(acceleration);
    velocity.limit(maxSpeed);
    position.add(velocity);
    acceleration.mult(0);
}


public void setPred(Predator pred) {
	this.pred = pred;
}

public List<Sheep> getSheepList()
{  return preyList;  }


} 
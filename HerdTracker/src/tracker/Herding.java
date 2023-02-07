package tracker;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Herding extends JFrame
{
	
    JFrame simFrame;
    Field field;
    ControlPanel controlPanel;
    Timer timer = null;
    
    int cntrlWidth, canWidth, canHeight;
	



public Herding(String[] args) 
{
/*
 * Creating the main frame
 */
 GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
 int width = gd.getDisplayMode().getWidth();
 canHeight= gd.getDisplayMode().getHeight();
 canWidth = (int) Math.round(width * 0.80);
 cntrlWidth = width - canWidth;
 System.out.println("full width: "+gd.getDisplayMode().getWidth());
 System.out.println("calc can width: "+canWidth);
 System.out.println("calc crl width: "+cntrlWidth);
        
 //create main frame, field and control panel
 simFrame = new JFrame("Sheep Movement");
 simFrame.setSize(width, canHeight);
 simFrame.setResizable(true);
 simFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

 field= new Field(canWidth,canHeight);
 field.setPreferredSize(new Dimension(canWidth, canHeight));
 field.setBorder(BorderFactory.createLineBorder(Color.black));
 field.setBackground(Color.white);
 System.out.println("field width"+field.fieldWidth);
 System.out.println("can width"+field.getWidth());
 
 controlPanel = new ControlPanel(cntrlWidth,canHeight);
 controlPanel.setPreferredSize(new Dimension(cntrlWidth, canHeight));
 controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
 controlPanel.setBackground(Color.LIGHT_GRAY);
 System.out.println("cntrl width"+controlPanel.getWidth());
// 
 Container content = simFrame.getContentPane();
content.add(controlPanel, BorderLayout.EAST);
content.add(field, BorderLayout.WEST);
 
 pack();
 simFrame.setLocationRelativeTo(null);
 simFrame.setVisible(true);

}

/*
 * Creating the canvas for simulation
 */
class Field extends JPanel {
 Animal animal;
 Sheep sheep;
 Predator predator;

 final int fieldWidth, fieldHeight;

 public Field(int canWidth, int canHeight) {
     
     this.fieldWidth = canWidth;
     this.fieldHeight = canHeight;

     predator = new Predator(10, 10,0, Color.RED);
     sheep = new Sheep(50, 50,0, Color.BLUE);
     
     createSim(0);
    

     timer = new Timer(30, new ActionListener(){
         public void actionPerformed(ActionEvent e)
         {

             simFrame.repaint();
             field.repaint();
         }
     });
 }

	private void createSim(int numAnimals) {
		System.out.println("inside create sim");
		animal = Animal.create(100, 100, 0, Color.BLUE);       
   }

	private void createSheep(int numPrey) {
		System.out.println("inside create herd");
		sheep = Sheep.create(100, 100, numPrey, Color.BLUE);
		
   }
	
	private void createPredator(int numPreds) {
		System.out.println("inside create herd");
		predator = Predator.create(50, 50, numPreds, Color.RED);       
   }
	


 @Override
 public void paintComponent(Graphics g2) {
     super.paintComponent(g2);
     Graphics2D g = (Graphics2D) g2;
     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);
     animal.run(g);
     sheep.run(g);
     predator.run(g);

 }
	
}

/*
 * Control pannel for inputting parameters
 */
class ControlPanel extends JPanel {
 final int cntrlWidth, cntrlHeight;
 JTextField sheepCount;
 JTextField predatorCount;
 JTextField dZoneCount;
 
 public ControlPanel(int cntrlWidth, int cntrlHeight) {
     this.cntrlWidth = cntrlWidth;
     this.cntrlHeight = cntrlHeight;
		// Buttons 
     /*
      * Button to add sheeps to the canvas
      */
     JButton startButton = new JButton("Sheep");
     startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("clicked sheep");
				int numPrey = Integer.parseInt(sheepCount.getText());
				field.createSheep(numPrey);
				
                simFrame.repaint();
                //field.repaint();
                timer.start();
			}

     }); 
     
     /*
      * button to add predators to the canvas
      */
     JButton predatorButton = new JButton("Predator");
     predatorButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("clicked predator");
				int numPreds = Integer.parseInt(predatorCount.getText());
				field.createPredator(numPreds);
				
				simFrame.repaint();
                //field.repaint();
                timer.start();
			}

     }); 
     
     /*
      * Button to add Dangerzone to the canvas
      */
     JButton dZoneButton = new JButton("DangerZone");
     predatorButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("clicked dzone");
			}

     }); 
     /*
      * Button to stop the stimulation
      */
     JButton stopButton = new JButton("Stop");
     stopButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            timer.stop();
         }
     });




     //Parameters panel : sheepCount, predatorCount, dZoneCount
     JPanel textParametersPane = new JPanel();
     textParametersPane.setPreferredSize(new Dimension(cntrlWidth-10, canHeight/5));
     textParametersPane.setBorder(BorderFactory.createTitledBorder("General parameters"));

    
     sheepCount = new JTextField("", 20);
     sheepCount.setHorizontalAlignment(JTextField.LEFT);
     JLabel sheepLabel = new JLabel("Number of Sheeps:");
     sheepLabel.setLabelFor(sheepCount);
     

     
     
     predatorCount = new JTextField("", 20);
     predatorCount.setHorizontalAlignment(JTextField.LEFT);
     JLabel predatorLabel = new JLabel("Number of Predators:");
     predatorLabel.setLabelFor(predatorCount);
     
     dZoneCount = new JTextField("", 20);
     dZoneCount.setHorizontalAlignment(JTextField.LEFT);
     JLabel dZoneCountLabel = new JLabel("Number of danger zones:");
     predatorLabel.setLabelFor(dZoneCount);
     
     //Add components to general parameters' panel
     textParametersPane.add(sheepLabel);
     textParametersPane.add(sheepCount);
     
     textParametersPane.add(predatorLabel);
     textParametersPane.add(predatorCount);
     
     textParametersPane.add(dZoneCountLabel);
     textParametersPane.add(dZoneCount);
     
     add(textParametersPane);
              
     add(startButton);
     add(predatorButton);
     add(dZoneButton);
     add(stopButton);
     


     pack();
     
     setVisible(true); 
 }
 



} 



//--------------MAIN----------------------

public static void main(String[] args)
{ 
	Herding herding = new Herding(args);

}

} 

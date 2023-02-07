# zhawProjects
# Herd Tracker
##### Sakthipriya M S
###### 
* * *
## About

Wolf attacks need be sensed with animal location sensors and/or cameras and alerts should be send out to shepherds when presence of predators are spotted.
The purpose of this study is to model herd behavior using a simulator in the presence of predator and compare it with the actual data in order to predict predator attacks.


- - -
## Why a Herd Tracker?
Based on data from all European countries summarized for the period 2012-2016 estimated
* 1,000-1,250 wolverines
* 8,000 – 9,000 Eurasian lynx
* 15,000- 16,000 brown bears
* 17,000 wolves present in continental Europe
In continental Europe sheep are by far the livestock species most often associated with livestock depredation by all large carnivores, with goats also vulnerable in the southern countries where they are abundant.
For wolves 71% of all cases were sheep, for bears 65% of all cases were sheep, for lynx 45% of all cases were sheep and for wolverines 45% of all cases were sheep. 

- - -
## Sheep Bhaviour
Sheep within a population attempt to reduce their predation risk by putting other conspecifics between themselves and predators.
sheep exhibit a strong attraction towards the centre of the flock under threat due to this behaviour.

- - -
## Simulation
Herd movement model can be created based on Reynolds’ three rules - Cohesion, Separation, Alignment.
* Cohesion: handles flock centering, steering the sheep towards the average position of its neighbors. 
* Alignment: aligns the sheep’s velocity with the average direction of its neighbours, causing it to head in the same direction. 
* Separation: steers the sheep away from neighbors that are too close in order to avoid crowding or collision.

- - -
## Implementation
* A simulation of flocking of herd is created using Reynolds model in Java.
* Each sheep is a separate thread and moves according to Reynolds rules.
* The Predators are represented by red dots and prey is represented by blue dots
* The velocities of all animals are calculated.
* The positions of all animals are updated by adding the velocities to the current positions
* Once the predator thread is added, then the sheep movements should be steered towards the centre as distance between predator and sheep decreases.
* The sheep's velocity should increase when the predator is close by.
* Danger zones are identified and alerts are sent out when 

- - -
## How to run

Compile project files with java compiler , then run Herding.java.
The UI consists of Panes for inputting the number of Preys, Predators and Danger zones.
The buttons for creating each of them are also available upon clicking the buttons the simulation of each category will be live.


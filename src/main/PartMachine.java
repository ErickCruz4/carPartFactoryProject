package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import data_structures.ListQueue;
import interfaces.Queue;
/**
 * Machine that produces car parts on a conveyor belt
 * This machine has a conveyorbelt, timer and produces car parts based on a set period,
 * weight errors, and chances of defective parts.
 */
public class PartMachine {
	
	 	private int id;                             // Unique identifier for the machine
	    private CarPart p1;                         // Reference car part used as a template
	    private int period;                         // time period for producing the car parts
	    private double weightError;                 //  weight error for produced parts
	    private int chanceOfDefective;              // Chance of producing a defective part
	    private Queue<Integer> timer;               // This is the timer for regulating part production
	    private Queue<CarPart> conveyorBelt;        // The conveyorbelt for transporting produced parts
	    private int totalPartsProduced;             // This is the total number of parts that was produces by the machine
	
    
	 public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
    	this.id = id;
        this.p1 = p1;
        this.period = period;
        this.weightError = weightError;
        this.chanceOfDefective = chanceOfDefective;
        this.totalPartsProduced = 0;
        createTimer();
        createConveyorBelt();
    }
	 /**
	     * Initializes the timer and has the values of the production period
	     */
    public void createTimer() {
    	int max = this.period - 1;
    	int min = 0;
    	timer = new ListQueue<Integer>();
    	for(int i = max; i >= min; i--) {
    		timer.enqueue(i);
    	}
    }
    /**
     * Creates the conveyorbelt using the specified values (null) = empty spaces 
     */
    public void createConveyorBelt() {
    	conveyorBelt = new ListQueue<CarPart>();
    	for(int i = 0; i < 10; i++) {
    		conveyorBelt.enqueue(null);
    	}
    }
        
    public int getId() {
       return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Queue<Integer> getTimer() {
       return this.timer;
    }
    public void setTimer(Queue<Integer> timer) {
    	this.timer = timer;
    }
    public CarPart getPart() {
       return this.p1;
    }
    public void setPart(CarPart part1) {
        this.p1 = part1;
    }
    public Queue<CarPart> getConveyorBelt() {
        return this.conveyorBelt;
    }
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	 this.conveyorBelt = conveyorBelt;
    }
    public int getTotalPartsProduced() {
         return this.totalPartsProduced;
    }
    public void setTotalPartsProduced(int count) {
    	this.totalPartsProduced = count;
    }
    public double getPartWeightError() {
        return this.weightError;
    }
    public void setPartWeightError(double partWeightError) {
        this.weightError = partWeightError;
    }
    public int getChanceOfDefective() {
        return this.chanceOfDefective;
    }
    public void setChanceOfDefective(int chanceOfDefective) {
        this.chanceOfDefective = chanceOfDefective;
    }
    /**
     * 
     * This basically executes the timer or advances the timer by 1 tick and returns the prev tick
     * @return the value of the prev tick
     */
    public int tickTimer() {
    	int pastTick = timer.dequeue();
    	timer.enqueue(pastTick);
    	return pastTick;
    }
    /**
     * Does the function of resetting the conveyorbelt calls the method above to create a new one
     */
    public void resetConveyorBelt() {
    	 conveyorBelt.clear();
    	 createConveyorBelt();
    }
    /**
     * Produces a car part based on the timer and adds it to the conveyor belt.
     *
     * @return The car part produced and added to the conveyor belt.
     */
    public CarPart produceCarPart() {
    	if(timer.front() == 0) {
    		int id = p1.getId();
    		String name = p1.getName();
    		double weight = generateRandomWeight();
    		
    		boolean defective = totalPartsProduced % chanceOfDefective == 0;
    		
    		CarPart part = new CarPart(id, name, weight, defective);
    		conveyorBelt.enqueue(part);
    		totalPartsProduced++;
    		tickTimer();
    	} else {
    		conveyorBelt.enqueue(null);
    		tickTimer();
    	}
    	
    	return conveyorBelt.dequeue();
    }
    /**
     * This generates a random weight that is called in the produceCartPart
     *
     * @return randomly generated weight for a car part
     */
    public double generateRandomWeight() {
    	double max = p1.getWeight() + weightError;
		double min = p1.getWeight() - weightError;
		
		double rn = new Random().nextDouble();
		return min + (rn * (max - min));
    }
    
    
    /**
     * Returns string representation of a Part Machine in the following format:
     * Machine {id} Produced: {part name} {total parts produced}
     */
    @Override
    public String toString() {
        return "Machine " + this.getId() + " Produced: " + this.getPart().getName() + " " + this.getTotalPartsProduced();
    }
    /**
     * Prints the content of the conveyor belt. 
     * The machine is shown as |Machine {id}|.
     * If the is a part it is presented as |P| and an empty space as _.
     */
    public void printConveyorBelt() {
        // String we will print
        String str = "";
        // Iterate through the conveyor belt
        for(int i = 0; i < this.getConveyorBelt().size(); i++){
            // When the current position is empty
            if (this.getConveyorBelt().front() == null) {
                str = "_" + str;
            }
            // When there is a CarPart
            else {
                str = "|P|" + str;
            }
            // Rotate the values
            this.getConveyorBelt().enqueue(this.getConveyorBelt().dequeue());
        }
        System.out.println("|Machine " + this.getId() + "|" + str);
    }
}

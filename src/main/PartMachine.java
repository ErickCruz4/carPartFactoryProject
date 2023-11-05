package main;

import interfaces.Queue;

public class PartMachine {
   
    public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
        
    }
    public int getId() {
       
    }
    public void setId(int id) {
        
    }
    public Queue<Integer> getTimer() {
       
    }
    public void setTimer(Queue<Integer> timer) {
        
    }
    public CarPart getPart() {
       
    }
    public void setPart(CarPart part1) {
        
    }
    public Queue<CarPart> getConveyorBelt() {
        
    }
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	
    }
    public int getTotalPartsProduced() {
         
    }
    public void setTotalPartsProduced(int count) {
    	
    }
    public double getPartWeightError() {
        
    }
    public void setPartWeightError(double partWeightError) {
        
    }
    public int getChanceOfDefective() {
        
    }
    public void setChanceOfDefective(int chanceOfDefective) {
        
    }
    public void resetConveyorBelt() {
        
    }
    public int tickTimer() {
       
    }
    public CarPart produceCarPart() {
       
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

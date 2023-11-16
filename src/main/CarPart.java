package main;
/**
 * This creates a car part with unique identification, name, weight, and defect status.
 * This class provides methods to access and modify the parts of a car part.
 */
public class CarPart {
    
		private int id;             // Unique identifier for the car part
	    private String name;        // Name of the car part
	    private double weight;      // Weight of the car part 
	    private Boolean isDefective; // Indicates whether the car part is defective 
    
	    /**
	     * Constructs a new CarPart with the specified attributes.
	     *
	     * @param id          The unique identifier for the car part
	     * @param name        The name of the car part
	     * @param weight      The weight of the car part in kilograms
	     * @param isDefective Indicates whether the car part is defective
	     */
    public CarPart(int id, String name, double weight, boolean isDefective) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.isDefective = isDefective;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getWeight() {
      return this.weight;
    }
    public void setWeight(double weight) {
      this.weight = weight;
    }

    public boolean isDefective() {
      return this.isDefective;
    }
    public void setDefective(boolean isDefective) {
        this.isDefective = isDefective;
    }
    /**
     * Returns the parts name as its string representation
     * @return (String) The part name
     */
    public String toString() {
        return this.getName();
    }
}
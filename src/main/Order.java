package main;

import interfaces.Map;
/**
 * Order.java is basically an order placed by a customer for specific parts.
 * The order includes an identifier, customer name, a mapping of requested parts and quantities,
 * indicating if an order was fulfilled
 */
public class Order {
	
	private int id;
	private String customerName;
	private Map<Integer, Integer> requestedParts;
	private Boolean fulfilled;
	
	 /**
     * Constructs a new Order with the specified attributes.
     *
     * @param id             The unique identifier for the order
     * @param customerName   The name of the customer placing the order
     * @param requestedParts Mapping of part IDs to quantities requested
     * @param fulfilled      Indicates whether the order has been fulfilled 
     */
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
        this.id = id;
        this.customerName = customerName;
        this.requestedParts = requestedParts;
        this.fulfilled = fulfilled;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isFulfilled() {
      return this.fulfilled;
    }
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
    public Map<Integer, Integer> getRequestedParts() {
       return this.requestedParts;
    }
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
       this.requestedParts = requestedParts;
    }
    public String getCustomerName() {
      return this.customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * Returns the order's information in the following format: {id} {customer name} {number of parts requested} {isFulfilled}
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}

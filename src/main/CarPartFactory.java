package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import data_structures.LinkedStack;
import data_structures.SinglyLinkedList;
import interfaces.HashFunction;
import interfaces.List;
import interfaces.Map;
import interfaces.Stack;
/**
 *  Stack: LinkedStack is used as a LIFO structure to represent the production bin.
	Map: The HashTableSC is used for partCatalog, inventory etc for it's very fast access using keys.
	Readers: BufferedReader is used for efficient file reading due to its buffering capabilities.
	Hash Function: The hashF is used for its ease of use in hashing integers.
	List: SinglyLinkedL was mainly used because of it's efficiency when removing.
 */
/**
 * CarPartFactory is basically a factory where we produce/create and manage cars
 * Uses various data structures for efficient storage and retrieval of info used
 */
public class CarPartFactory {

	private List<PartMachine> partMachine;       // List of machines producing the car parts
    private Stack<CarPart> productionBin;        // Stack representing the production bin
    private Map<Integer, CarPart> partCatalog;   // Map storing information about each car part
    private Map<Integer, List<CarPart>> inventory; // Map representing the inventory of produced parts
    private List<Order> orders;                  // List of customer orders
    private Map<Integer, Integer> defectives;    // Map tracking the number of defective parts produced
	 
	/**
     * Constructs a CarPartFactory using information from order and parts files.
     *
     * @param orderPath this is the path to the file containing the information from the CSV that has order info
     * @param partsPath path to the file containing car part information.
     * @throws IOException If an I/O error occurs while reading the files.
     */
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
    	setupMachines(partsPath);
    	setupOrders(orderPath);
    	setupInventory();
    	setupDefectives();
    	setupProductionBin();
    }
    public List<PartMachine> getMachines() {
    	return this.partMachine;
    }
    public void setMachines(List<PartMachine> machines) {
        this.partMachine = machines;
    }
    public Stack<CarPart> getProductionBin() {
    	return this.productionBin;
    }
    public void setProductionBin(Stack<CarPart> production) {
       this.productionBin = production;
    }
    public Map<Integer, CarPart> getPartCatalog() {
        return this.partCatalog;
    }
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
        this.partCatalog = partCatalog;
    }
    public Map<Integer, List<CarPart>> getInventory() {
       return this.inventory;
    }
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
        this.inventory = inventory;
    }
    public List<Order> getOrders() {
        return this.orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public Map<Integer, Integer> getDefectives() {
        return this.defectives;
    }
    public void setDefectives(Map<Integer, Integer> defectives) {
        this.defectives = defectives;
    }
    // Getter and setter methods for the various attributes

    /**
     * This sets up the order by reading the info from the file and initializes the orders list
     *
     * @param path to the file containing the info
     * @throws IOException If an I/O error occurs while reading the file
     */
    public void setupOrders(String path) throws IOException {
    	String pathOrders = path; 
        BufferedReader BReader = new BufferedReader(new FileReader(pathOrders));
        orders = new SinglyLinkedList<Order>();
        String currLine = BReader.readLine();
        boolean skipsFirstLineCsv = true;

        while (currLine != null) {
            if (!skipsFirstLineCsv) {
            	Order order = parseOrder(currLine);
                orders.add(order);
            }
            skipsFirstLineCsv = false;
            currLine = BReader.readLine();
        }

        BReader.close();
    }
    /**
     * Parses a line of order information and creates an Order object
     *
     * @param line of order information from the file
     * @return parsed Order object
     */
    public Order parseOrder(String line) {
    	HashFunction<Integer> hashFunction = new BasicHashFunction();
    	Map<Integer, Integer> requestedParts = new HashTableSC<Integer, Integer>(1, hashFunction);
    	// Split the order information into an array using comma as the separator.
    	String[] orderInfo = line.split("\\s*,\\s*");
    	int id = Integer.parseInt(orderInfo[0]);
        String customerName = orderInfo[1];
        String[] unparsedParts = orderInfo[2].split("-");
        for(String set : unparsedParts) {
        	// parenthesis is removed and quantity using space as the separator.
        	set = set.replace("(", "").replace(")", ""); 
        	String[] splitSet = set.split(" ");
        	int key = Integer.parseInt(splitSet[0]);
        	int value = Integer.parseInt(splitSet[1]);
        	// Add the part and quantity to the hash map of requested parts.
        	requestedParts.put(key, value);
        }
        
        Order order = new Order(id, customerName, requestedParts, false);
		return order;
    }
    /**
     * This read the info of the car part and machine from a file and initializes partCatalog and partMachine
     * Here we use data structures to facilitate the tasks 
     * @param path Path to the file containing car part information
     * @throws IOException If an I/O error occurs while reading the file
     */
    public void setupMachines(String path) throws IOException {
    	String pathOrders = path; 
    	// Initialize a buffered reader in order to read file
    	BufferedReader BReader = new BufferedReader(new FileReader(pathOrders));
        HashFunction<Integer> hashFunction = new BasicHashFunction();
        partCatalog = new HashTableSC<Integer, CarPart>(1, hashFunction);
        partMachine = new SinglyLinkedList<PartMachine>();
        String currLine = BReader.readLine();
        boolean skipsFirstLineCsv = true;

        while (currLine != null) {
            //skips first line because it is not needed / does not apply
        	if (!skipsFirstLineCsv) {
            	CarPart parsedPart = parsePart(currLine);
            	 //enter the parsed car part information into partCatalog.
            	partCatalog.put(parsedPart.getId(), parsedPart);
            	PartMachine parsedMachine = parseMachine(currLine, parsedPart);
            	partMachine.add(parsedMachine);
            }
            skipsFirstLineCsv = false;
            currLine = BReader.readLine();
        }

        BReader.close();
    }
    /**
     * Parses a line of car part information in order to extract info and creates a CarPart object
     *
     * @param line A line of car part information from the file
     * @return The parsed CarPart object
     */
    public CarPart parsePart(String line) {
    	//We split the line using an array to split info
    	String[] partInfo = line.split("\\s*,\\s*");
    	int id = Integer.parseInt(partInfo[0]);
        String partName = partInfo[1];
        double weight = Double.parseDouble(partInfo[2]);
        boolean isDefective = false;
        //We create a new carpPart with our new elms
        CarPart part = new CarPart(id, partName, weight, isDefective);
		return part;
    }
    /**
     * This also parses a line of info (machine) and creates an object (PartMachine)
     * This method also does (almost) the exact same thing as parsePart
     * @param  machine information from the file
     * @param  CarPart object associated with the machine
     * @return parsed PartMachine object
     */
    public PartMachine parseMachine(String line, CarPart part) {
    	String[] machineInfo = line.split("\\s*,\\s*");
    	int id = Integer.parseInt(machineInfo[0]);
        int period = Integer.parseInt(machineInfo[4]);
        double weightError = Double.parseDouble(machineInfo[3]);
        int chanceOfDefective = Integer.parseInt(machineInfo[5]);
        
        PartMachine machine = new PartMachine(id, part, period, weightError, chanceOfDefective);
        
		return machine;
    }
    /**
     * Initializes the inventory map with empty lists for each car part
     * Uses a hash function and a separate chaining HashTable for efficient storage
     */
    public void setupInventory() {
        HashFunction<Integer> hashFunction = new BasicHashFunction();
        inventory = new HashTableSC<Integer, List<CarPart>>(1 ,hashFunction);
        List<Integer> keys = partCatalog.getKeys();
     // This creates an empty list for each car part and puts it into the inventory
        for(int i : keys) {
        	List<CarPart> list = new SinglyLinkedList<CarPart>();
        	inventory.put(i, list);
        }
    }
    /**
     * Initializes the defectives map with count set to zero for each car part
     * Uses a hash function and a separate chaining HashTable for better storage
     */
    public void setupDefectives() {
        HashFunction<Integer> hashFunction = new BasicHashFunction();
        // We initialize the defective ones as a hash map to track the number parts that are defective
        defectives = new HashTableSC<Integer, Integer>(1, hashFunction);
     // Retrieves the keys (part IDs) from the partCatalog
        List<Integer> keys = partCatalog.getKeys();
    	for(int key : keys) {
    		defectives.put(key, 0);
    	}
    }
    /**
     * Initializes the production bin as an empty LinkedStack for storing produced car parts
     */
    public void setupProductionBin() {
    	productionBin = new LinkedStack<CarPart>();
    }
    /**
     * Moves car parts from the production bin to inventory and monitors defective parts
     * If a part is identified as defective, the defect count is increased; otherwise, the part is included in the inventory
     */
    public void storeInInventory() {
       while(!productionBin.isEmpty()) {
    	   CarPart topPart = productionBin.pop();
    	   if(topPart.isDefective()) {
    		   int partId = topPart.getId();
    		   int dPartCount = defectives.get(partId);
    		   defectives.put(partId, dPartCount + 1);
    	   } else {
    		   int partId = topPart.getId();
    		   inventory.get(partId).add(topPart);;
    	   }
       }
   
    }
    /**
     * Simulates the operation of the factory over a specified period and processes customer orders
     * Produces car parts, stores them in the production bin, and then processes the orders
     *
     * @param days    The number of days to run the factory
     * @param minutes The number of minutes per day
     */
    public void runFactory(int days, int minutes) {
    	int runningTime = days * minutes;
        //Does a simulation of a running factory for a specified time slot
    	while(runningTime > 0) {
        	for(PartMachine m : partMachine) {
        		CarPart producedPart = m.produceCarPart();
        		if(producedPart != null) {
        			productionBin.push(producedPart);
        		}
        	}
        	runningTime--;
        }
        // This runs the factory 10 extra loops
        for(int i = 0; i < 10; i++) {
        	//We use the machines to create carParts 
        	for(PartMachine m : partMachine) {
        		CarPart producedPart = m.produceCarPart();
        		if(producedPart != null) {
        			productionBin.push(producedPart);
        		}
        	}
        }
        storeInInventory();
        processOrders();
    }

    /**
     * Processes customer orders, updates inventory and sets fulfillment status
     * Checks if there are enough parts in inventory in order to fulfill each order
     * Also, if applicable, deducts the requested parts from inventory; otherwise, marks the order unfulfilled
     */
    public void processOrders() {
        for(Order customerOrder : orders) {
        	boolean canFulfill = true;
        	 // Gets the keys that belong to each part that was requested in the order
        	List<Integer> keys = customerOrder.getRequestedParts().getKeys();
        	for(int key : keys) {
        		if(customerOrder.getRequestedParts().get(key) > inventory.get(key).size()) {
        			canFulfill = false;
        		}
        	}
        	// If the order can be fulfilled we subtract the parts that were requested from the inventory
        	if(canFulfill) {
        		for(int key : keys) {
        			int orderAmount = customerOrder.getRequestedParts().get(key);
        			for(int i = 0; i < orderAmount; i++) {
        				inventory.get(key).remove(0);
        			}
        		}
        	}
        	//Sets the status of the order 
        	customerOrder.setFulfilled(canFulfill);
        }
    }
    /**
     * Generates a report indicating how many parts were produced per machine,
     * how many of those were defective and are still in inventory. Additionally, 
     * it also shows how many orders were successfully fulfilled. 
     */
    public void generateReport() {
        String report = "\t\t\tREPORT\n\n";
        report += "Parts Produced per Machine\n";
        for (PartMachine machine : this.getMachines()) {
            report += machine + "\t(" + 
            this.getDefectives().get(machine.getPart().getId()) +" defective)\t(" + 
            this.getInventory().get(machine.getPart().getId()).size() + " in inventory)\n";
        }
       
        report += "\nORDERS\n\n";
        for (Order transaction : this.getOrders()) {
            report += transaction + "\n";
        }
        System.out.println(report);
    }

   

}


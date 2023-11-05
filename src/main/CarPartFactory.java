package main;

public class CarPartFactory {

        
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
                
    }
    public List<PartMachine> getMachines() {
       
    }
    public void setMachines(List<PartMachine> machines) {
        
    }
    public Stack<CarPart> getProductionBin() {
      
    }
    public void setProductionBin(Stack<CarPart> production) {
       
    }
    public Map<Integer, CarPart> getPartCatalog() {
        
    }
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
        
    }
    public Map<Integer, List<CarPart>> getInventory() {
       
    }
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
        
    }
    public List<Order> getOrders() {
        
    }
    public void setOrders(List<Order> orders) {
        
    }
    public Map<Integer, Integer> getDefectives() {
        
    }
    public void setDefectives(Map<Integer, Integer> defectives) {
        
    }

    public void setupOrders(String path) throws IOException {
       
    }
    public void setupMachines(String path) throws IOException {
       
    }
    public void setupCatalog() {
        
    }
    public void setupInventory() {
        
    }
    public void storeInInventory() {
       
    }
    public void runFactory(int days, int minutes) {
        
    }

   
    public void processOrders() {
        
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

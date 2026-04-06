import java.util.Scanner;

class Customer {
    private final String customerId;
    private String name;

    public Customer(String customerId, String name, String address) {
        this.customerId = customerId;
        setName(name);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
}

abstract class UtilityAccount {
    protected Customer customer;
    private int unitsConsumed;
    protected double billAmount;

    public UtilityAccount(Customer customer, int unitsConsumed) {
        this.customer = customer;
        setUnitsConsumed(unitsConsumed);
    }

    public void setUnitsConsumed(int units) {
        if (units >= 0) {
            this.unitsConsumed = units;
        } else {
            throw new IllegalArgumentException("Units cannot be negative");
        }
    }

    public int getUnitsConsumed() { return unitsConsumed; }

    public abstract void calculateBill();
    public abstract void generateBill();
}

class ElectricityAccount extends UtilityAccount {
    private static final double RATE = 6.0;

    public ElectricityAccount(Customer customer, int unitsConsumed) {
        super(customer, unitsConsumed);
    }

    @Override
    public void calculateBill() {
        billAmount = getUnitsConsumed() * RATE;
        if (billAmount > 1800) {
            billAmount += billAmount * 0.10;
        }
    }

    @Override
    public void generateBill() {
        System.out.println("Electricity Bill for " + customer.getName());
        System.out.println("Units: " + getUnitsConsumed() + " | Total: $" + billAmount);
    }
}

class WaterAccount extends UtilityAccount {
    private static final double RATE = 2.0;

    public WaterAccount(Customer customer, int unitsConsumed) {
        super(customer, unitsConsumed);
    }

    @Override
    public void calculateBill() {
        billAmount = getUnitsConsumed() * RATE;
        if (getUnitsConsumed() > 500) {
            billAmount += 150;
        }
    }

    @Override
    public void generateBill() {
        System.out.println("Water Bill for " + customer.getName());
        System.out.println("Units: " + getUnitsConsumed() + " | Total: $" + billAmount);
    }
}

public class rr {
    public static void main(String[] args) {
        Customer myCustomer = new Customer("U001", "D.M. Dinusha", "Monaragala");

        UtilityAccount electricityAccount = new ElectricityAccount(myCustomer, 350);
        UtilityAccount waterAccount = new WaterAccount(myCustomer, 600);

        electricityAccount.calculateBill();
        electricityAccount.generateBill();
        System.out.println("-------------------------");

        waterAccount.calculateBill();
        waterAccount.generateBill();
        System.out.println("-------------------------");



        // Seat reservation 
        Scanner s = new Scanner(System.in);

        int row=5;
        int col=8;

        String seats[][] = new String[row][col];

        int count = 1;
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                seats[i][j] = String.valueOf(count++);
            }
        }

        while(true){
            System.out.println("\n-- Current Seat Map --");
            for(int i=0; i<row; i++){
                for(int j=0; j<col; j++){
                    System.out.printf("%-5s", seats[i][j]);
                }
                System.out.println();
            }
            System.out.print("\nEnter seat number to reserve (or type '0' to exit) : ");
            String input = s.nextLine();

            if(input.equals("0")){
                System.out.println("Exiting system. Goodbye!");
                break;
            }

            try {
                int seatNum = Integer.parseInt(input);
                if(seatNum < 1 || seatNum > 40){
                    System.out.println("Error: Invalid seat number.please choose between 1 and 49.");
                    continue;
                }

                int r = (seatNum-1)/col;
                int c = (seatNum-1)%col;

                if(seats[r][c].equals("X")){
                    System.out.println("OK");
                }else{
                    seats[r][c] = "X";
                    System.out.println("Seat sucsessfully reserved");
                }
            

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numerical seat number.");
            }

            
        }
    }
}
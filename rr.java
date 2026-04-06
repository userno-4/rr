
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
    }
}
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

// Batsman Bowlers

abstract class Player{
    protected int playerID;
    protected String name;
    protected String country;
    protected int[] score;

    public Player(int playerID, String name, String country, int[]initialScore){
        this.playerID = playerID;
        this.name = name;
        this.country = country;
        this.score = new int[initialScore.length];
        for(int i=0; i<initialScore.length; i++){
            this.score[i] = initialScore[i];
        }
    }

    public int getPlayId(int playerID){
        return playerID;
    }
    public String getName(String name){
        return name;
    }
    public String getCountry(String country){
        return country;
    }

    abstract void addScore(int score);
    abstract void printDetails();

    protected void expandAndAddScore(int score){
        int[] newScore = new int[this.score.length + 1];

        for(int i=0; i<this.score.length; i++){
            newScore[i] = this.score[i];
        }

        newScore[this.score.length] = score;
        this.score = newScore;
    }
}

class Batsman extends Player{
    private int runs;

    Batsman(int playerID, String name,String country,  int[]initialScore){
        super(playerID, name, country, initialScore); 
        calculateTotalRuns();
    }

    public int getRuns(){
        return runs;
    }

    private void calculateTotalRuns(){
        this.runs = runs;
        for(int s : score){
            this.runs += s;
        }
    }

    @Override
    public void addScore(int score){

        if(score >= 0 && score <= 100){
            expandAndAddScore(score);
            calculateTotalRuns();
        }

    }

    @Override
    public void printDetails(){
        
        System.out.printf("\n\nBatsman ID : %d \nName : %s \nCountry : %s \nRuns : %n\nScore : " , playerID,name,country,runs );
        printScore();
    }

    private void printScore(){
        System.out.print("{");
        for(int i=0; i<score.length; i++){
            System.out.print(score[i] + (i == score.length -1 ? "" : ", "));
        }
        System.out.print("}");
    }

}

class Bowlers extends Player{
    protected int wicket;

    Bowlers(int playerID, String name,String country,  int[]initialScore){
        super(playerID, name, country, initialScore); 
        calculateTotalwickets();
    }

    private void calculateTotalwickets(){
        this.wicket = 0;
        for(int s: score){
            this.wicket += s;
        }
    }
    
    @Override
    public void addScore(int score){

        if(score >= 0 && score <= 100){
            expandAndAddScore(score);
            calculateTotalwickets();
        }else{
            System.out.println("Invalud wickets for Bowlers.");
        }

    }



    public int getWicket(int wicket){
        return wicket;
    }

    @Override
    public void printDetails(){
        
        System.out.printf("\n\nBatsman ID : %d \nName : %s \nCountry : %s \nWickets : %d \nTotal Wickets : " , playerID,name,country, wicket );
        printScore();
    }

    private void printScore(){
        System.out.print("{");
        for(int i=0; i<score.length; i++){
            System.out.print(score[i] + (i == score.length-1 ? "" : ", "));
        }
        System.out.print("}");
    }
}

public class Q2try {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        
        Batsman[] batsman = {
            new Batsman(1, "Sachin Tenulkar", "India", new int[]{42,0,61,15,0,100,23,64,41,26}),
            new Batsman(2, "Virat Kohli", "India", new int[]{0,25,10,60,11,0,40,11,0,2,0}),
            new Batsman(3,"MS Dhoni", "India",new int[]{6,22,48,0,11,69,0,21,19,0,42}),
            new Batsman(4,"Ricky Ponting","Australia",new int[]{11,0,28,0,64,18,45,26,0,11,0}),
            new Batsman(5,"Brian Lara","West Indies",new int[]{0,13,21,47,0,69,17,0,12,0,40})

        };

        Bowlers[] bowlers = {
            new Bowlers(1,"Muttiah Murlitharan","Sri Lanka",new int[]{0,10,6,8,0,1,0,2,4,0,6}),
            new Bowlers(2,"Shame Warne","Australia",new int[]{6,0,1,0,2,0,4,10,0,6,8}),
            new Bowlers(3,"Wanindu Hasaranga","Sri Lanka",new int[]{0,1,0,6,8,4,2,0,6,0,10}),
            new Bowlers(4,"Glenn McGrath","Australia",new int[]{4,2,6,0,10,0,1,8,6,0,0}),
            new Bowlers(5,"Dale Sten","South Africa",new int[]{0,4,0,2,1,6,10,0,8,6,0})
        };

        System.out.println( "--------- Batsman Details ---------");
        for(int i=0; i<5; i++){
            batsman[i].printDetails();
        }

        System.out.println( "\n--------- Bowlers Details ---------");
        for(int i=0; i<5; i++){
            bowlers[i].printDetails();
        }


    }
}


// car Exhibitions
import java.util.ArrayList;

abstract class Car {
    protected double price;
    protected int year;

    public Car(double price, int year) {
        this.price = price;
        this.year = year;
    }

    public abstract double calculateSalePrice();

    @Override
    public String toString() {
        return "Car [Year: " + year + ", Original Price: $" + price + 
               ", Sale Price: $" + calculateSalePrice() + "]";
    }

    public double getPrice() {
        return price;
    }
    
    public int getYear() {
        return year;
    }
}

class ClassicCar extends Car {

    public ClassicCar(double price, int year) {
        super(price, year);
    }

    @Override
    public double calculateSalePrice() {
        return price * 0.10;
    }
    
    @Override
    public String toString() {
        return "ClassicCar [Year: " + year + ", Original Price: $" + price + 
               ", Sale Price: $" + calculateSalePrice() + "]";
    }
}

class SportCar extends Car {

    public SportCar(double price, int year) {
        super(price, year);
    }

    @Override
    public double calculateSalePrice() {
        if (year > 2015) {
            return price * 1.75;
        } else if (year > 2005) {
            return price * 1.50;
        } else {
            return price * 1.25;
        }
    }
    
    @Override
    public String toString() {
        return "SportCar [Year: " + year + ", Original Price: $" + price + 
               ", Sale Price: $" + calculateSalePrice() + "]";
    }
}

class CarExhibition {
    private ArrayList<Car> cars;

    public CarExhibition() {
        cars = new ArrayList<>();
    }

    public void addCar(double price, int year) {
        Car genericCar = new Car(price, year) {
            @Override
            public double calculateSalePrice() {
                return price;
            }
            
            @Override
            public String toString() {
                return "GenericCar [Year: " + year + ", Original Price: $" + price + 
                       ", Sale Price: $" + calculateSalePrice() + "]";
            }
        };
        cars.add(genericCar);
    }

    public void addSportCar(double price, int year) {
        SportCar sportCar = new SportCar(price, year);
        cars.add(sportCar);
    }

    public void addClassicCar(double price, int year) {
        ClassicCar classicCar = new ClassicCar(price, year);
        cars.add(classicCar);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Car car : cars) {
            total += car.getPrice();
        }
        return total;
    }

    public double getTotalSalePrice() {
        double total = 0;
        for (Car car : cars) {
            total += car.calculateSalePrice();
        }
        return total;
    }

    public void displayAllCars() {
        System.out.println("\n=== CARS IN EXHIBITION ===");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
        System.out.println("\nTotal Original Price: $" + getTotalPrice());
        System.out.println("Total Sale Price: $" + getTotalSalePrice());
    }
}

public class CarExhibitionDemo {
    public static void main(String[] args) {
        CarExhibition exhibition = new CarExhibition();

        System.out.println("=== CAR EXHIBITION MANAGEMENT SYSTEM ===\n");

        System.out.println("Adding Classic Cars:");
        exhibition.addClassicCar(50000, 1965);
        exhibition.addClassicCar(75000, 1978);
        exhibition.addClassicCar(120000, 1957);

        System.out.println("\nAdding Sport Cars:");
        exhibition.addSportCar(80000, 2020);
        exhibition.addSportCar(60000, 2010);
        exhibition.addSportCar(45000, 2000);

        System.out.println("\nAdding Generic Cars:");
        exhibition.addCar(25000, 2018);
        exhibition.addCar(30000, 2012);

        exhibition.displayAllCars();

        System.out.println("\n\n=== DETAILED SALE PRICE CALCULATIONS ===");

        ClassicCar classic = new ClassicCar(100000, 1960);
        System.out.println("Classic Car (1960, $100,000):");
        System.out.println("  Sale Price (10%): $" + classic.calculateSalePrice());

        SportCar sport1 = new SportCar(100000, 2020);
        SportCar sport2 = new SportCar(100000, 2010);
        SportCar sport3 = new SportCar(100000, 2000);

        System.out.println("\nSport Car (2020, $100,000):");
        System.out.println("  Sale Price (75% higher): $" + sport1.calculateSalePrice());

        System.out.println("\nSport Car (2010, $100,000):");
        System.out.println("  Sale Price (50% higher): $" + sport2.calculateSalePrice());

        System.out.println("\nSport Car (2000, $100,000):");
        System.out.println("  Sale Price (25% higher): $" + sport3.calculateSalePrice());

        System.out.println("\n=== POLYMORPHISM DEMONSTRATION ===");
        Car[] carArray = {
            new ClassicCar(50000, 1970),
            new SportCar(80000, 2022),
            new ClassicCar(35000, 1965),
            new SportCar(60000, 2008)
        };
        
        for (Car car : carArray) {
            System.out.println(car);
        }
    }
}

// Roman Convert Number
import java.util.Scanner;

public class Main{
	
	public static int convertToInt(char roman){
		char symbols[] = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
		int values[] = {1, 5, 10, 50, 100, 500, 1000};
		int value = 0;
		boolean isFound = false;
		for(int i = 0; i < symbols.length; i++)
			if(roman == symbols[i]){
				value = values[i];
				isFound = true;
			}
			
		if(!isFound){
			System.out.println("Invalid Roman Symbol!");
			System.exit(1);
		}
		
		return value;
	}
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter Roman number: ");
		String romanNum = sc.next().trim().toUpperCase();
		
		int value = convertToInt(romanNum.charAt(0));
		
		for(int i = 1; i < romanNum.length(); i++){
			int currentVal = convertToInt(romanNum.charAt(i));
			int prevVal = convertToInt(romanNum.charAt(i - 1));
			
			value += currentVal;
			
			if(currentVal > prevVal)
				value -= prevVal * 2;
		}
		
		System.out.printf("%S => %d", romanNum, value);
	}
}
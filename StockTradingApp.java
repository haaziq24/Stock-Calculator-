import java.util.Scanner;

/**
 * StockTradingApp - Main driver class for the Stock Trading application
 * Provides a menu-driven interface for buying/selling stocks and tracking capital gains
 */
public class StockTradingApp {
    private static StockTracker tracker;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        tracker = new StockTracker();
        scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Stock Trading Application!");
        System.out.println("=========================================\n");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    handleBuy();
                    break;
                case 2:
                    handleSell();
                    break;
                case 3:
                    handleDisplayGains();
                    break;
                case 4:
                    running = false;
                    System.out.println("\nThank you for using the Stock Trading Application!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please select 1-4.");
            }
            
            if (running) {
                System.out.println(); // Blank line for readability
            }
        }
        
        scanner.close();
    }
    
    /**
     * Displays the main menu
     */
    private static void displayMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Buy shares");
        System.out.println("2. Sell shares");
        System.out.println("3. Display total realized capital gain");
        System.out.println("4. Quit");
        System.out.print("\nEnter your choice: ");
    }
    
    /**
     * Gets and validates menu choice from user
     * @return valid menu choice (1-4)
     */
    private static int getMenuChoice() {
        while (!scanner.hasNextInt()) {
            scanner.next(); // Clear invalid input
            System.out.print("Invalid input. Please enter a number (1-4): ");
        }
        return scanner.nextInt();
    }
    
    /**
     * Handles the Buy shares option
     */
    private static void handleBuy() {
        System.out.println("\n--- Buy Shares ---");
        
        try {
            System.out.print("Enter number of shares to buy: ");
            int numShares = getPositiveInt();
            
            System.out.print("Enter price per share: $");
            double pricePerShare = getPositiveDouble();
            
            tracker.buyShares(numShares, pricePerShare);
            
            System.out.printf("\nPurchase confirmed: %d shares at $%.2f per share\n", 
                numShares, pricePerShare);
            System.out.printf("Total cost: $%.2f\n", numShares * pricePerShare);
            
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    /**
     * Handles the Sell shares option
     */
    private static void handleSell() {
        System.out.println("\n--- Sell Shares ---");
        
        if (tracker.isEmpty()) {
            System.out.println("You don't own any shares to sell.");
            return;
        }
        
        System.out.printf("You currently own %d shares.\n", tracker.getCurrentShares());
        
        try {
            System.out.print("Enter number of shares to sell: ");
            int numShares = getPositiveInt();
            
            System.out.print("Enter selling price per share: $");
            double sellPrice = getPositiveDouble();
            
            double capitalGain = tracker.sellShares(numShares, sellPrice);
            
            System.out.printf("\nSale confirmed: %d shares at $%.2f per share\n", 
                numShares, sellPrice);
            System.out.printf("Total revenue: $%.2f\n", numShares * sellPrice);
            System.out.printf("Capital gain from this sale: $%.2f\n", capitalGain);
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    /**
     * Handles the Display total realized capital gain option
     */
    private static void handleDisplayGains() {
        System.out.println("\n--- Total Realized Capital Gain ---");
        double totalGain = tracker.getTotalRealizedGain();
        
        System.out.printf("Total realized capital gain: $%.2f\n", totalGain);
        
        if (totalGain > 0) {
            System.out.println("You have a net capital GAIN.");
        } else if (totalGain < 0) {
            System.out.println("You have a net capital LOSS.");
        } else {
            System.out.println("You have no capital gain or loss yet.");
        }
    }
    
    /**
     * Gets a positive integer from user
     * @return positive integer
     */
    private static int getPositiveInt() {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                if (value > 0) {
                    return value;
                }
                System.out.print("Please enter a positive number: ");
            } else {
                scanner.next(); // Clear invalid input
                System.out.print("Invalid input. Please enter a positive number: ");
            }
        }
    }
    
    /**
     * Gets a positive double from user
     * @return positive double
     */
    private static double getPositiveDouble() {
        while (true) {
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                if (value > 0) {
                    return value;
                }
                System.out.print("Please enter a positive number: ");
            } else {
                scanner.next(); // Clear invalid input
                System.out.print("Invalid input. Please enter a positive number: ");
            }
        }
    }
}
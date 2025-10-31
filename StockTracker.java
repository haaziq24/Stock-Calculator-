/**
 * StockTracker class manages a stock portfolio using FIFO (First-In-First-Out) 
 * accounting for capital gains calculations.
 */
public class StockTracker {
    private LinkedDeque<StockLot> ledger;
    private double totalRealizedGain;
    
    /**
     * Constructor initializes the ledger and total realized gain
     */
    public StockTracker() {
        ledger = new LinkedDeque<>();
        totalRealizedGain = 0.0;
    }
    
    /**
     * Buys shares and adds them to the ledger
     * @param numShares number of shares to buy
     * @param pricePerShare price per share
     */
    public void buyShares(int numShares, double pricePerShare) {
        if (numShares <= 0) {
            throw new IllegalArgumentException("Number of shares must be positive");
        }
        if (pricePerShare <= 0) {
            throw new IllegalArgumentException("Price per share must be positive");
        }
        
        StockLot newLot = new StockLot(numShares, pricePerShare);
        ledger.addToBack(newLot);
    }
    
    /**
     * Sells shares using FIFO method and calculates capital gains
     * @param numShares number of shares to sell
     * @param sellPricePerShare selling price per share
     * @return capital gain/loss from this sale
     * @throws IllegalStateException if trying to sell more shares than owned
     */
    public double sellShares(int numShares, double sellPricePerShare) {
        if (numShares <= 0) {
            throw new IllegalArgumentException("Number of shares must be positive");
        }
        if (sellPricePerShare <= 0) {
            throw new IllegalArgumentException("Sell price per share must be positive");
        }
        
        // Check if we have enough shares to sell
        int totalShares = getTotalShares();
        if (numShares > totalShares) {
            throw new IllegalStateException("Cannot sell " + numShares + 
                " shares. Only " + totalShares + " shares available.");
        }
        
        double capitalGainFromSale = 0.0;
        int sharesToSell = numShares;
        
        // Process sale using FIFO
        while (sharesToSell > 0) {
            StockLot frontLot = ledger.getFront();
            int sharesInLot = frontLot.getShares();
            double purchasePrice = frontLot.getBuyPrice();
            
            if (sharesToSell >= sharesInLot) {
                // Sell entire lot
                capitalGainFromSale += (sellPricePerShare - purchasePrice) * sharesInLot;
                ledger.removeFront();
                sharesToSell -= sharesInLot;
            } else {
                // Sell partial lot
                capitalGainFromSale += (sellPricePerShare - purchasePrice) * sharesToSell;
                
                // Create new lot with remaining shares and add back to front
                int remainingShares = sharesInLot - sharesToSell;
                ledger.removeFront();
                StockLot remainingLot = new StockLot(remainingShares, purchasePrice);
                ledger.addToFront(remainingLot);
                
                sharesToSell = 0;
            }
        }
        
        // Update total realized gain
        totalRealizedGain += capitalGainFromSale;
        
        return capitalGainFromSale;
    }
    
    /**
     * Gets the total realized capital gain/loss from all sales
     * @return total realized capital gain
     */
    public double getTotalRealizedGain() {
        return totalRealizedGain;
    }
    
    /**
     * Helper method to count total shares in ledger
     * @return total number of shares currently owned
     */
    private int getTotalShares() {
        int total = 0;
        LinkedDeque<StockLot> temp = new LinkedDeque<>();
        
        // Count shares while preserving ledger
        while (!ledger.isEmpty()) {
            StockLot lot = ledger.removeFront();
            total += lot.getShares();
            temp.addToBack(lot);
        }
        
        // Restore ledger
        while (!temp.isEmpty()) {
            ledger.addToBack(temp.removeFront());
        }
        
        return total;
    }
    
    /**
     * Gets the current number of shares in the ledger
     * @return total shares owned
     */
    public int getCurrentShares() {
        return getTotalShares();
    }
    
    /**
     * Checks if the ledger is empty
     * @return true if no shares owned
     */
    public boolean isEmpty() {
        return ledger.isEmpty();
    }
}
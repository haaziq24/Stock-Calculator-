
/**
 * Write a description of class StockLot here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StockLot
{
    private int shares;
    private double buyPrice; 
    
    public StockLot(int s, double p)
    {
        shares = s;
        buyPrice = p;
    }
    
    public int getShares()
    {
        return shares;
    }
    
    public double getBuyPrice()
    {
        return buyPrice;
    }
	
	//Used to take out some of the shares from the lot
	public void setShares(int newShares)
	{
		if (newShares < shares)
			shares = newShares;
	}
}

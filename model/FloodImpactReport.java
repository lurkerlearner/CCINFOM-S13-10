package model;

public class FloodImpactReport
{
    private int ClientID;
    private String Name;
    private String Street;
    private String City;
    private String RiskByFactor;
    private double RiskByAWL;
    private int Sales;

    public FloodImpactReport(int cID, String n, String st, String c,
                             String rbff, double rbawl, int sl)
                             {
                                this.ClientID = cID;
                                this.Name = n;
                                this.Street = st;
                                this.City = c;
                                this.RiskByFactor = rbff;
                                this.RiskByAWL = rbawl;
                                this.Sales = sl;
                             }

    public FloodImpactReport() {}
    
    public int getClientID()
    {
        return this.ClientID;
    }

    public String getName()
    {
        return this.Name;
    }

    public String getStreet()
    {
        return this.Street;
    }

    public String getCity()
    {
        return this.City;
    }

    public String getRiskByFactor()
    {
        return this.RiskByFactor;
    }

    public double getRiskByAWL()
    {
        return this.RiskByAWL;
    }

    public int getSales()
    {
        return this.Sales;
    }
}
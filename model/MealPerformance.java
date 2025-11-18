package model;

public class MealPerformance {
    
    private int meal_Id;
    private String meal_name;
    private int TimesOrdered;
    private float TotalRevenue;
    private int distinctlocations;

    public MealPerformance(int meal_Id, String meal_name, int TimesOrdered, float TotalRevenue, int distinctlocations){
        this.meal_Id = meal_Id;
        this.meal_name = meal_name;
        this.TimesOrdered = TimesOrdered;
        this.TotalRevenue = TotalRevenue;
        this.distinctlocations = distinctlocations;
    }

    public int getMeal_id(){
        return meal_Id;
    }
    public String getMeal_name(){
        return meal_name;
    }

    public int getTimesOrdered(){
        return TimesOrdered;
    }
    public float getTotalRevenue(){
        return TotalRevenue;
    }
    public int getDistinctLocations(){
        return distinctlocations;
    }    
}

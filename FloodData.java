public class FloodData
{
    private int flood_id;
    private String flood_factor;
    private int location_id;
    private String alt_delivery_method;
    private int affected_households;
    private float avg_water_level;
    private String road_condition;
    private boolean special_packaging;

    public FloodData(int flood_id, String flood_factor, int location_id, String alt_delivery_method, 
                     int affected_households, float avg_water_level, String road_condition, 
                     boolean special_packaging)
                     {
                        this.flood_id = flood_id;
                        this.flood_factor = flood_factor;
                        this.location_id = location_id;
                        this.alt_delivery_method = alt_delivery_method;
                        this.affected_households = affected_households;
                        this.avg_water_level = avg_water_level;
                        this.road_condition = road_condition;
                        this.special_packaging = special_packaging;
                     }

    public int getFloodID()
    {
        return flood_id;
    }

    public String getFloodFactor()
    {
        return flood_factor;
    }

    public int getLocationID()
    {
        return location_id;
    }

    public String getAltDeliveryMethod()
    {
        return alt_delivery_method;
    }

    public int getAffectedHouseholds()
    {
        return affected_households;
    }

    public float getAvgWaterLevel()
    {
        return avg_water_level;
    }

    public String getRoadCondition()
    {
        return road_condition;
    }

    public boolean getSpecialPackaging()
    {
        return special_packaging;
    }
    
    public void setFloodID(int flood_id)
    {
        this.flood_id = flood_id;
    }

    public void setFloodFactor(String flood_factor)
    {
        this.flood_factor = flood_factor;
    }

    public void setLocationID(int location_id)
    {
        this.location_id = location_id;
    }

    public void setAltDeliveryMethod(String alt_delivery_method)
    {
        this.alt_delivery_method = alt_delivery_method;
    }

    public void setAffectedHouseholds(int affected_households)
    {
        this.affected_households = affected_households;
    }

    public void setAvgWaterLevel(float avg_water_level)
    {
        this.avg_water_level = avg_water_level;
    }

    public void setRoadCondition(String road_condition)
    {
        this.road_condition = road_condition;
    }

    public void setSpecialPackaging(boolean special_packaging)
    {
        this.special_packaging = special_packaging;
    }
}
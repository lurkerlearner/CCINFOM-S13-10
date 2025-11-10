import java.sql.*;
import java.util.ArrayList;

public class FloodDataDAO 
{
    private Connection c;

    public FloodDataDAO(Connection connection)
    {
        this.c = connection;
    }

    // MAIN OPERATIONS (create, select by pk, select all, delete)

    // Insert a new flood data record into the table
    public void addFloodData(FloodData fd) 
    {
        String sql = "INSERT INTO flood_data (flood_factor, avg_water_level, " +
                     "affected_households, road_condition, special_packaging, " +
                     "alt_delivery_method, location_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, fd.getFloodFactor().name());
            stmt.setFloat(2, fd.getAvgWaterLevel());
            stmt.setInt(3, fd.getAffectedHouseholds());
            stmt.setString(4, fd.getRoadCondition().name());
            stmt.setBoolean(5, fd.getSpecialPackaging());
            stmt.setString(6, fd.getAltDeliveryMethod().name());
            stmt.setInt(7, fd.getLocationID());
            
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) 
            {
                int generatedID = rs.getInt(1);
                fd.setFloodID(generatedID);
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // HELPER METHOD (for building flood data in methods)
    public FloodData copyFloodData(ResultSet rs) throws SQLException
    {
        FloodData fd = new FloodData();

        fd.setFloodID(rs.getInt("flood_id"));
        fd.setFloodFactor(FloodFactor.valueOf(rs.getString("flood_factor").toUpperCase()));
        fd.setAvgWaterLevel(rs.getFloat("avg_water_level"));
        fd.setAffectedHouseholds(rs.getInt("affected_households"));
        fd.setRoadCondition(RoadCondition.valueOf(rs.getString("road_condition").toUpperCase().replace(" ", "_")));
        fd.setSpecialPackaging(rs.getBoolean("special_packaging"));
        fd.setAltDeliveryMethod(AltDeliveryMethod.valueOf(rs.getString("alt_delivery_method").toUpperCase().replace(" ", "_")));
        fd.setLocationID(rs.getInt("location_id"));

        return fd;
    }

    // Select a flood data record from the table by its primary key
    public FloodData getFloodDataByKey(int key)
    {
        String sql = "SELECT * FROM flood_data WHERE flood_id = ?";

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, key);

            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                return copyFloodData(rs);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    // Select all existing records in the flood data table
    public ArrayList<FloodData> getAllFloodData()
    {
        String sql = "SELECT * FROM flood_data";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs));  
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // Delete a record from the flood data table given a primary key
    public boolean deleteFloodData(int key)
    {
        String sql = "DELETE FROM flood_data WHERE flood_id = ?";

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, key);
            
            int deletedRows = stmt.executeUpdate();
            
            return deletedRows > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    // SIMPLE FILTERS (single field)

    // Select records with a specific flood factor
    public ArrayList<FloodData> getByFloodFactor(FloodFactor ff)
    {
        String sql = "SELECT * FROM flood_data WHERE flood_factor = ?";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setString(1, ff.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // Select records with an average water level matching or greater than specified
    public ArrayList<FloodData> getGreaterThanAvgWaterLevel(float avg)
    {
        String sql = "SELECT * FROM flood_data WHERE avg_water_level >= ?";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setFloat(1, avg);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs));  
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // Select records whose number of houses affected is at least what is given
    public ArrayList<FloodData> getByHousesAffected(int h)
    {
        String sql = "SELECT * FROM flood_data WHERE affected_households >= ?";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, h);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs));  
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // Select records with a specific road condition
    public ArrayList<FloodData> getByRoadCondition(RoadCondition rd)
    {
        String sql = "SELECT * FROM flood_data WHERE road_condition = ?";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setString(1, rd.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs));  
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // Select records with/without special packaging
    public ArrayList<FloodData> getBySpecialPackaging(boolean sp)
    {
        String sql = "SELECT * FROM flood_data WHERE special_packaging = ?";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setBoolean(1, sp);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // Select records with a specific alternative delivery method
    public ArrayList<FloodData> getByAltDeliveryMethod(AltDeliveryMethod adm)
    {
        String sql = "SELECT * FROM flood_data WHERE alt_delivery_method = ?";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setString(1, adm.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs));  
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // Select records with a specific location id
    public ArrayList<FloodData> getByLocation(int location)
    {
        String sql = "SELECT * FROM flood_data WHERE location_id = ?";
        ArrayList<FloodData> floody_areas = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, location);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                floody_areas.add(copyFloodData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return floody_areas;
    }

    // MORE ADVANCED FILTERS (joins, aggregates, sorts)
}

import java.sql.*;
import java.util.ArrayList;

public class RiderDAO 
{
    private Connection c;

    public RiderDAO(Connection connection)
    {
        this.c = connection;
    }

    // MAIN OPERATIONS (create, select by pk, select all, delete)

    // Insert a new rider record into the table
    public void addRider(Rider r) 
    {
        String sql = "INSERT INTO rider (rider_name, hire_date, contact_no) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, r.getRiderName());
            stmt.setDate(2, r.getHireDate());
            stmt.setString(3, r.getContactNo());
            
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) 
            {
                int generatedID = rs.getInt(1);
                r.setRiderID(generatedID);
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // HELPER METHOD (for building riders in methods)
    public Rider copyRiderData(ResultSet rs) throws SQLException
    {
        Rider r = new Rider();

        r.setRiderID(rs.getInt("rider_id"));
        r.setRiderName(rs.getString("rider_name"));
        r.setHireDate(rs.getDate("hire_date"));
        r.setContactNo(rs.getString("contact_no"));

        return r;
    }

    // Select a rider record from the table by its primary key
    public Rider getRiderByKey(int key)
    {
        String sql = "SELECT * FROM rider WHERE rider_id = ?";

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, key);

            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                return copyRiderData(rs);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    // Select all existing records in the rider table
    public ArrayList<Rider> getAllRiders()
    {
        String sql = "SELECT * FROM rider";
        ArrayList<Rider> riders = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                riders.add(copyRiderData(rs));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return riders;
    }

    // Delete a record from the rider table given a primary key
    public boolean deleteRider(int key)
    {
        String sql = "DELETE FROM rider WHERE rider_id = ?";

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

    // Select all riders hired before a date
    public ArrayList<Rider> getRidersHiredBefore(Date d)
    {
        String sql = "SELECT * FROM rider WHERE hire_date < ?";
        ArrayList<Rider> riders = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setDate(1, d);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                riders.add(copyRiderData(rs));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return riders;
    }

    // Select all riders hired after a date
    public ArrayList<Rider> getRidersHiredAfter(Date d)
    {
        String sql = "SELECT * FROM rider WHERE hire_date > ?";
        ArrayList<Rider> riders = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setDate(1, d);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                riders.add(copyRiderData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return riders;
    }

    // MORE ADVANCED FILTERS (joins, aggregates, sorts)
}

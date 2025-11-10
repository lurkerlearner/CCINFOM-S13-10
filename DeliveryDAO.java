import java.sql.*;
import java.util.ArrayList;

public class DeliveryDAO 
{
    private Connection c;

    public DeliveryDAO(Connection connection)
    {
        this.c = connection;
    }

    // MAIN OPERATIONS (create, select by pk, select all, delete)

    // Insert a new delivery record into the table
    public void addDelivery(Delivery d)
    {
        String sql = "INSERT INTO delivery (order_date, time_ordered, time_delivered, " +
                     "payment_mode, payment_status, delivery_method, delivery_status, " +
                     "client_id, meal_id, rider_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            
            stmt.setDate(1, d.getOrderDate());
            stmt.setTime(2, d.getTimeOrdered());
            stmt.setTime(3, d.getTimeDelivered());
            stmt.setString(4, d.getPaymentMode().name());
            stmt.setString(5, d.getPaymentStatus().name());
            stmt.setString(6, d.getDeliveryMethod().name());
            stmt.setString(7, d.getDeliveryStatus().name());
            stmt.setInt(8, d.getClientID());
            stmt.setInt(9, d.getMealID());
            stmt.setInt(10, d.getRiderID());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) 
            {
                int generatedID = rs.getInt(1);
                d.setTransactionID(generatedID);
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // HELPER METHOD (for building deliveries in methods)
    public Delivery copyDeliveryData(ResultSet rs) throws SQLException
    {
        Delivery d = new Delivery();

        d.setTransactionID(rs.getInt("transaction_id"));
        d.setOrderDate(rs.getDate("order_date"));
        d.setTimeOrdered(rs.getTime("time_ordered"));
        d.setTimeDelivered(rs.getTime("time_delivered"));
        d.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode").toUpperCase().replace(" ", "_")));
        d.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status").toUpperCase()));
        d.setDeliveryMethod(DeliveryMethod.valueOf(rs.getString("delivery_method").toUpperCase().replace(" ", "_")));
        d.setDeliveryStatus(DeliveryStatus.valueOf(rs.getString("delivery_status").toUpperCase().replace("-", "_")));
        d.setClientID(rs.getInt("client_id"));
        d.setMealID(rs.getInt("meal_id"));
        d.setRiderID(rs.getInt("rider_id"));

        return d;
    }

    // Select a delivery record from the table by its primary key
    public Delivery getDeliveryByKey(int key)
    {
        String sql = "SELECT * FROM delivery WHERE transaction_id = ?";

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, key);

            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                return copyDeliveryData(rs);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    // Select all existing records in the delivery table
    public ArrayList<Delivery> getAllDeliveries()
    {
        String sql = "SELECT * FROM delivery";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Delete a record from the delivery table given a primary key
    public boolean deleteDelivery(int key)
    {
        String sql = "DELETE FROM delivery WHERE transaction_id = ?";

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

    // Select all deliveries made by one client
    public ArrayList<Delivery> getDeliveriesByClient(int client)
    {
        String sql = "SELECT * FROM delivery WHERE client_id = ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, client);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries with a specific meal ordered
    public ArrayList<Delivery> getDeliveriesByMeal(int meal)
    {
        String sql = "SELECT * FROM delivery WHERE meal_id = ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, meal);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries made within a certain date range
    public ArrayList<Delivery> getDeliveriesWithinDateRange(Date d1, Date d2)
    {
        String sql = "SELECT * FROM delivery WHERE order_date >= ? AND order_date <= ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setDate(1, d1);
            stmt.setDate(2, d2);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries made before a date
    public ArrayList<Delivery> getDeliveriesBeforeDate(Date date)
    {
        String sql = "SELECT * FROM delivery WHERE order_date < ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setDate(1, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs));  
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries made after a date
    public ArrayList<Delivery> getDeliveriesAfterDate(Date date)
    {
        String sql = "SELECT * FROM delivery WHERE order_date > ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setDate(1, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries with a certain payment mode
    public ArrayList<Delivery> getDeliveriesByPaymentMode(PaymentMode pm)
    {
        String sql = "SELECT * FROM delivery WHERE payment_mode = ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setString(1, pm.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries with a certain payment status
    public ArrayList<Delivery> getDeliveriesByPaymentStatus(PaymentStatus ps)
    {
        String sql = "SELECT * FROM delivery WHERE payment_status = ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setString(1, ps.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries with a certain delivery method
    public ArrayList<Delivery> getDeliveriesByMethod(DeliveryMethod dm)
    {
        String sql = "SELECT * FROM delivery WHERE delivery_method = ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setString(1, dm.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // Select all deliveries handled by one rider
    public ArrayList<Delivery> getDeliveriesByRider(int rider)
    {
        String sql = "SELECT * FROM delivery WHERE rider_id = ?";
        ArrayList<Delivery> deliveries = new ArrayList<>();

        try (PreparedStatement stmt = c.prepareStatement(sql))
        {
            stmt.setInt(1, rider);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
            {
                deliveries.add(copyDeliveryData(rs));  
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deliveries;
    }

    // MORE ADVANCED FILTERS (joins, aggregates, sorts)


}

import java.sql.Date;
import java.sql.Time;

public class Delivery 
{
    private int transaction_id;
    private int client_id;
    private int meal_id;
    private Date order_date;
    private Time time_ordered;
    private Time time_delivered;
    private String payment_mode;
    private String payment_status;
    private int rider_id;
    private String delivery_status;
    private String delivery_method;

    public Delivery(int transaction_id, int client_id, int meal_id,
                    Date order_date, Time time_ordered, Time time_delivered,
                    int rider_id, String payment_mode, String payment_status,
                    String delivery_status, String delivery_method)
                    {
                        this.transaction_id = transaction_id;
                        this.client_id = client_id;
                        this.meal_id = meal_id;
                        this.order_date = order_date;
                        this.time_ordered = time_ordered;
                        this.time_delivered = time_delivered;
                        this.rider_id = rider_id;
                        this.payment_mode = payment_mode;
                        this.payment_status = payment_status;
                        this.delivery_status = delivery_status;
                        this.delivery_method = delivery_method;
                    }

    public int getTransactionID() 
    {
        return transaction_id;
    }

    public int getClientID()
    {
        return client_id;
    }

    public int getMealID()
    {
        return meal_id;
    }

    public Date getOrderDate()
    {
        return order_date;
    }

    public Time getTimeOrdered()
    {
        return time_ordered;
    }

    public Time getTimeDelivered()
    {
        return time_delivered;
    }

    public int getRiderID()
    {
        return rider_id;
    }

    public String getPaymentMode()
    {
        return payment_mode;
    }

    public String getPaymentStatus()
    {
        return payment_status;
    }

    public String getDeliveryStatus()
    {
        return delivery_status;
    }

    public String getDeliveryMethod()
    {
        return delivery_method;
    }

    public void setTransactionID(int transaction_id)
    {
        this.transaction_id = transaction_id;
    }

    public void setClientID(int client_id)
    {
        this.client_id = client_id;
    }

    public void setMealID(int meal_id)
    {
        this.meal_id = meal_id;
    }

    public void setOrderDate(Date order_date)
    {
        this.order_date = order_date;
    }

    public void setTimeOrdered(Time time_ordered)
    {
        this.time_ordered = time_ordered;
    }

    public void setTimeDelivered(Time time_delivered)
    {
        this.time_delivered = time_delivered;
    }

    public void setRiderID(int rider_id)
    {
        this.rider_id = rider_id;
    }

    public void setPaymentMode(String payment_mode)
    {
        this.payment_mode = payment_mode;
    }

    public void setPaymentStatus(String payment_status)
    {
        this.payment_status = payment_status;
    }

    public void setDeliveryStatus(String delivery_status)
    {
        this.delivery_status = delivery_status;
    }

    public void setDeliveryMethod(String delivery_method)
    {
        this.delivery_method = delivery_method;
    }
}

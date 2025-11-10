/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

/*
 *
 * @author faith
 */

public enum Restock_status {
    AVAILABLE, LOW_STOCK, OUT_OF_STOCK;

    public static Restock_status calculateStatus(double stock_quantity) {
    if (stock_quantity == 0) {
            return OUT_OF_STOCK;
        } else if (stock_quantity > 0 && stock_quantity <= 30) {
            return LOW_STOCK;
        } else {
            return AVAILABLE;
        }
}
}


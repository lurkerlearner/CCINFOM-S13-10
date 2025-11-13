package controller;

import DAO.SupplierDAO;
import model.Supplier;

public class SupplierController {
    private final SupplierDAO supplierDAO;

    public SupplierController(SupplierDAO dao) {
        this.supplierDAO = dao;
    }

    public boolean addSupplier(String supplier_name, int contact_no, int alt_contact_no, int location_id) {
        Supplier supplier = new Supplier();
        supplier.setSupplier_name(supplier_name);
        supplier.setContact_no(contact_no);
        supplier.setAlt_contact_no(alt_contact_no);
        supplier.setLocation_id(location_id);

        return supplierDAO.addSupplier(supplier);
    }

    public boolean deleteSupplier(int supplier_id) {
        return supplierDAO.deleteSupplier(supplier_id);
    }
}

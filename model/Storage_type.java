package model;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

/**
 *
 * @author faith
 */
public enum Storage_type {
    DRY("Dry"), 
    FROZEN("Frozen"), 
    REFRIGERATED("Refrigerated");

    private final String dbValue;

    Storage_type(String dbValue) 
    {
        this.dbValue = dbValue;
    }

    public String getDbValue() 
    {
        return dbValue;
    }

    // converts db string value to java enum because java enums needs to be all caps and no spaces
    public static Storage_type fromDbValue(String dbVal) 
    {
        return valueOf(dbVal.toUpperCase());
    }
}

package com.leadcampusapp;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Shripad on 08-07-2018.
 */

class vmmaterial implements KvmSerializable{


    private long slno;
    private String MeterialName;
    private float MeterialCost;

    public vmmaterial(){

    }

    public vmmaterial(long slno, String meterialName, float meterialCost) {
        this.slno = slno;
        MeterialName = meterialName;
        MeterialCost = meterialCost;
    }

    public long getSlno() {
        return slno;
    }

    public void setSlno(long slno) {
        this.slno = slno;
    }

    public String getMeterialName() {
        return MeterialName;
    }

    public void setMeterialName(String meterialName) {
        MeterialName = meterialName;
    }

    public float getMeterialCost() {
        return MeterialCost;
    }

    public void setMeterialCost(float meterialCost) {
        MeterialCost = meterialCost;
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}

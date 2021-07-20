package com.leadcampusapp;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by User on 10/16/2018.
 */

public class SerializeModule extends Vector<String> implements KvmSerializable {

    private String fileName;
    private byte[] fileData;
    private ArrayList<Integer> fileInt;

    public ArrayList<Integer> getFileInt() {
        return fileInt;
    }

    public void setFileInt(ArrayList<Integer> fileInt) {
        this.fileInt = fileInt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
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

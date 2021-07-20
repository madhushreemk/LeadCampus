package com.leadcampusapp;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Shripad on 08-07-2018.
 */

public class AddProjectObject implements KvmSerializable {
    ArrayList<vmmaterial> materials;
    String Title;
    long Beneficiaries;
    String Objectives;
    String ActionPlan;
    long PDId;
    String Lead_Id;

    public AddProjectObject(){

    }

/*    public AddProjectObject(ArrayList<vmmaterial> matModel, String title, String beneficiaries, String objectives, String actionPlan, long PDId) {
        this.vmmaterial = matModel;
        Title = title;
        Beneficiaries = beneficiaries;
        Objectives = objectives;
        ActionPlan = actionPlan;
        this.PDId = PDId;
    }*/

   /* public ArrayList<vmmaterial> getMatModel() {
        return materials;
    }

    public void setMatModel(ArrayList<vmmaterial> matModel) {
        this.materials = matModel;
    }*/

    public ArrayList<vmmaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<vmmaterial> materials) {
        this.materials = materials;
    }

    public String getLead_Id() {
        return Lead_Id;
    }

    public void setLead_Id(String lead_Id) {
        Lead_Id = lead_Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getBeneficiaries() {
        return Beneficiaries;
    }

    public void setBeneficiaries(long beneficiaries) {
        Beneficiaries = beneficiaries;
    }

    public String getObjectives() {
        return Objectives;
    }

    public void setObjectives(String objectives) {
        Objectives = objectives;
    }

    public String getActionPlan() {
        return ActionPlan;
    }

    public void setActionPlan(String actionPlan) {
        ActionPlan = actionPlan;
    }

    public long getPDId() {
        return PDId;
    }

    public void setPDId(long PDId) {
        this.PDId = PDId;
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

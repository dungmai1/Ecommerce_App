package com.maidanhdung.ecommerce.ApiAddress;

public class Ward {
    String district_id, ward_id, ward_name,ward_type;

    public Ward(String district_id, String ward_id, String ward_name, String ward_type) {
        this.district_id = district_id;
        this.ward_id = ward_id;
        this.ward_name = ward_name;
        this.ward_type = ward_type;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getWard_type() {
        return ward_type;
    }

    public void setWard_type(String ward_type) {
        this.ward_type = ward_type;
    }
}
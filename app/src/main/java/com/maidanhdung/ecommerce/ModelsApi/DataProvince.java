package com.maidanhdung.ecommerce.ModelsApi;

import java.util.List;

public class DataProvince {
    private int ProvinceID;
    private String ProvinceName;
    private int CountryID;
    private String Code;
    private List<String> NameExtension;
    private int IsEnable;
    private int RegionID;
    private int RegionCPN;
    private int UpdatedBy;
    private String CreatedAt;
    private String UpdatedAt;
    private boolean CanUpdateCOD;
    private int Status;
    private String UpdatedIP;
    private int UpdatedEmployee;
    private String UpdatedSource;
    private String UpdatedDate;

    public DataProvince(int provinceID, String provinceName, int countryID, String code, List<String> nameExtension, int isEnable, int regionID, int regionCPN, int updatedBy, String createdAt, String updatedAt, boolean canUpdateCOD, int status, String updatedIP, int updatedEmployee, String updatedSource, String updatedDate) {
        ProvinceID = provinceID;
        ProvinceName = provinceName;
        CountryID = countryID;
        Code = code;
        NameExtension = nameExtension;
        IsEnable = isEnable;
        RegionID = regionID;
        RegionCPN = regionCPN;
        UpdatedBy = updatedBy;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        CanUpdateCOD = canUpdateCOD;
        Status = status;
        UpdatedIP = updatedIP;
        UpdatedEmployee = updatedEmployee;
        UpdatedSource = updatedSource;
        UpdatedDate = updatedDate;
    }

    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public int getCountryID() {
        return CountryID;
    }

    public void setCountryID(int countryID) {
        CountryID = countryID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public List<String> getNameExtension() {
        return NameExtension;
    }

    public void setNameExtension(List<String> nameExtension) {
        NameExtension = nameExtension;
    }

    public int getIsEnable() {
        return IsEnable;
    }

    public void setIsEnable(int isEnable) {
        IsEnable = isEnable;
    }

    public int getRegionID() {
        return RegionID;
    }

    public void setRegionID(int regionID) {
        RegionID = regionID;
    }

    public int getRegionCPN() {
        return RegionCPN;
    }

    public void setRegionCPN(int regionCPN) {
        RegionCPN = regionCPN;
    }

    public int getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public boolean isCanUpdateCOD() {
        return CanUpdateCOD;
    }

    public void setCanUpdateCOD(boolean canUpdateCOD) {
        CanUpdateCOD = canUpdateCOD;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getUpdatedIP() {
        return UpdatedIP;
    }

    public void setUpdatedIP(String updatedIP) {
        UpdatedIP = updatedIP;
    }

    public int getUpdatedEmployee() {
        return UpdatedEmployee;
    }

    public void setUpdatedEmployee(int updatedEmployee) {
        UpdatedEmployee = updatedEmployee;
    }

    public String getUpdatedSource() {
        return UpdatedSource;
    }

    public void setUpdatedSource(String updatedSource) {
        UpdatedSource = updatedSource;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }
}

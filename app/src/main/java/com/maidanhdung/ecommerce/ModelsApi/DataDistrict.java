package com.maidanhdung.ecommerce.ModelsApi;

import java.util.List;

public class DataDistrict {
    private int DistrictID;
    private int ProvinceID;
    private String DistrictName;
    private String Code;
    private int Type;
    private int SupportType;
    private List<String> NameExtension;
    private int IsEnable;
    private int UpdatedBy;
    private String CreatedAt;
    private String UpdatedAt;
    private boolean CanUpdateCOD;
    private int Status;
    private int PickType;
    private int DeliverType;
    private WhiteListClient WhiteListClient;
    private WhiteListDistrict WhiteListDistrict;
    private String ReasonCode;
    private String ReasonMessage;
    private List<String> OnDates;
    private String UpdatedIP;
    private int UpdatedEmployee;
    private String UpdatedSource;
    private String UpdatedDate;

    // Getters and setters for the fields

    public static class WhiteListClient {
        private List<Integer> From;
        private List<Integer> To;
        private List<Integer> Return;

        // Getters and setters for the fields
    }

    public static class WhiteListDistrict {
        private List<Integer> From;
        private List<Integer> To;
    }

    public DataDistrict(int districtID, int provinceID, String districtName, String code, int type, int supportType, List<String> nameExtension, int isEnable, int updatedBy, String createdAt, String updatedAt, boolean canUpdateCOD, int status, int pickType, int deliverType, DataDistrict.WhiteListClient whiteListClient, DataDistrict.WhiteListDistrict whiteListDistrict, String reasonCode, String reasonMessage, List<String> onDates, String updatedIP, int updatedEmployee, String updatedSource, String updatedDate) {
        DistrictID = districtID;
        ProvinceID = provinceID;
        DistrictName = districtName;
        Code = code;
        Type = type;
        SupportType = supportType;
        NameExtension = nameExtension;
        IsEnable = isEnable;
        UpdatedBy = updatedBy;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        CanUpdateCOD = canUpdateCOD;
        Status = status;
        PickType = pickType;
        DeliverType = deliverType;
        WhiteListClient = whiteListClient;
        WhiteListDistrict = whiteListDistrict;
        ReasonCode = reasonCode;
        ReasonMessage = reasonMessage;
        OnDates = onDates;
        UpdatedIP = updatedIP;
        UpdatedEmployee = updatedEmployee;
        UpdatedSource = updatedSource;
        UpdatedDate = updatedDate;
    }

    public int getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(int districtID) {
        DistrictID = districtID;
    }

    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getSupportType() {
        return SupportType;
    }

    public void setSupportType(int supportType) {
        SupportType = supportType;
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

    public int getPickType() {
        return PickType;
    }

    public void setPickType(int pickType) {
        PickType = pickType;
    }

    public int getDeliverType() {
        return DeliverType;
    }

    public void setDeliverType(int deliverType) {
        DeliverType = deliverType;
    }

    public DataDistrict.WhiteListClient getWhiteListClient() {
        return WhiteListClient;
    }

    public void setWhiteListClient(DataDistrict.WhiteListClient whiteListClient) {
        WhiteListClient = whiteListClient;
    }

    public DataDistrict.WhiteListDistrict getWhiteListDistrict() {
        return WhiteListDistrict;
    }

    public void setWhiteListDistrict(DataDistrict.WhiteListDistrict whiteListDistrict) {
        WhiteListDistrict = whiteListDistrict;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public void setReasonCode(String reasonCode) {
        ReasonCode = reasonCode;
    }

    public String getReasonMessage() {
        return ReasonMessage;
    }

    public void setReasonMessage(String reasonMessage) {
        ReasonMessage = reasonMessage;
    }

    public List<String> getOnDates() {
        return OnDates;
    }

    public void setOnDates(List<String> onDates) {
        OnDates = onDates;
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


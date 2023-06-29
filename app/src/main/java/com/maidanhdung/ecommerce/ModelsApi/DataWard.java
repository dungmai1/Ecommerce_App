package com.maidanhdung.ecommerce.ModelsApi;

import java.util.List;

public class DataWard {
    private String WardCode;
    private int DistrictID;
    private String WardName;
    private List<String> NameExtension;
    private int IsEnable;
    private boolean CanUpdateCOD;
    private int UpdatedBy;
    private String CreatedAt;
    private String UpdatedAt;
    private int SupportType;
    private int PickType;
    private int DeliverType;
    private WhiteListClient WhiteListClient;
    private WhiteListWard WhiteListWard;
    private int Status;
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
    public static class WhiteListWard {
        private List<Integer> From;
        private List<Integer> To;

        // Getters and setters for the fields
    }

    public DataWard(String wardCode, int districtID, String wardName, List<String> nameExtension, int isEnable, boolean canUpdateCOD, int updatedBy, String createdAt, String updatedAt, int supportType, int pickType, int deliverType, DataWard.WhiteListClient whiteListClient, DataWard.WhiteListWard whiteListWard, int status, String reasonCode, String reasonMessage, List<String> onDates, String updatedIP, int updatedEmployee, String updatedSource, String updatedDate) {
        WardCode = wardCode;
        DistrictID = districtID;
        WardName = wardName;
        NameExtension = nameExtension;
        IsEnable = isEnable;
        CanUpdateCOD = canUpdateCOD;
        UpdatedBy = updatedBy;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        SupportType = supportType;
        PickType = pickType;
        DeliverType = deliverType;
        WhiteListClient = whiteListClient;
        WhiteListWard = whiteListWard;
        Status = status;
        ReasonCode = reasonCode;
        ReasonMessage = reasonMessage;
        OnDates = onDates;
        UpdatedIP = updatedIP;
        UpdatedEmployee = updatedEmployee;
        UpdatedSource = updatedSource;
        UpdatedDate = updatedDate;
    }

    public String getWardCode() {
        return WardCode;
    }

    public void setWardCode(String wardCode) {
        WardCode = wardCode;
    }

    public int getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(int districtID) {
        DistrictID = districtID;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
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

    public boolean isCanUpdateCOD() {
        return CanUpdateCOD;
    }

    public void setCanUpdateCOD(boolean canUpdateCOD) {
        CanUpdateCOD = canUpdateCOD;
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

    public int getSupportType() {
        return SupportType;
    }

    public void setSupportType(int supportType) {
        SupportType = supportType;
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

    public DataWard.WhiteListClient getWhiteListClient() {
        return WhiteListClient;
    }

    public void setWhiteListClient(DataWard.WhiteListClient whiteListClient) {
        WhiteListClient = whiteListClient;
    }

    public DataWard.WhiteListWard getWhiteListWard() {
        return WhiteListWard;
    }

    public void setWhiteListWard(DataWard.WhiteListWard whiteListWard) {
        WhiteListWard = whiteListWard;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
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


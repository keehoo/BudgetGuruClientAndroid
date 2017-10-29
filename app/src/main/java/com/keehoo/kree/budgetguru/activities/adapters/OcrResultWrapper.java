package com.keehoo.kree.budgetguru.activities.adapters;

/**
 * Created by krzysztof on 29.10.2017.
 */

public class OcrResultWrapper {

    private String receiptTotalValue;
    private String receiptDate;
    private String receiptTime;
    private String receiptCategory;
    private String receiptNIP;
    private String receiptCompanyName;

    public String getReceiptTotalValue() {
        return receiptTotalValue;
    }

    public void setReceiptTotalValue(String receiptTotalValue) {
        this.receiptTotalValue = receiptTotalValue;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getReceiptCategory() {
        return receiptCategory;
    }

    public void setReceiptCategory(String receiptCategory) {
        this.receiptCategory = receiptCategory;
    }

    public String getReceiptNIP() {
        return receiptNIP;
    }

    public void setReceiptNIP(String receiptNIP) {
        this.receiptNIP = receiptNIP;
    }

    public String getReceiptCompanyName() {
        return receiptCompanyName;
    }

    public void setReceiptCompanyName(String receiptCompanyName) {
        this.receiptCompanyName = receiptCompanyName;
    }

}

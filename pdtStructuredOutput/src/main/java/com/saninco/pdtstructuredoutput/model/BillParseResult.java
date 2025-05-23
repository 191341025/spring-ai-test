package com.saninco.pdtstructuredoutput.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BillParseResult {
    private String barcode;
    private String paperAccountNumber;
    private String accountNumber;
    private String originalInvoiceNumber;
    private Date invoiceDate;
    private Date invoiceDueDate;
    private Date invoiceStartDate;
    private Date invoiceEndDate;
    private Double invoiceCurrentDue;
    private Double invoiceTotalDue;
    private Double mrcAmount;
    private Double occAmount;
    private Double usageAmount;
    private Double lpcAmount;
    private Double taxAmount;
    private Double adjustmentAmount;
    private Double invoicePreviousBalance;
    private Double invoicePreviousAdjustment;
    private Double invoicePreviousPayment;
    private Double invoiceBalanceForward;
    private List<BillItem> invoiceItems = new ArrayList<BillItem>();

    @Getter
    @Setter
    public static class BillItem {
        private String itemBarCode;
        private String itemTypeId;
        private String itemName;
        private Double itemAmount;
        private Double rate;
        private Double quantity;
        private Date startDate;
        private Date endDate;
        private String taxCodeId;
        private String taxNumber;
        private String description;
        private String circuitNumber;
        private Date itemDate;
        private String location;
        private String chargeType;
        // ...
    }
}

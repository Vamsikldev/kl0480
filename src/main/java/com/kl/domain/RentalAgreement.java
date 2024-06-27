package com.kl.domain;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a rental agreement for a tool rental service.
 */
public class RentalAgreement {
    public String toolCode;
    public String toolType;
    public String toolBrand;
    public int rentalDays;
    public Date checkOutDate;
    public Date dueDate;
    public double dailyRentalCharge;
    public int chargeDays;
    public double preDiscountCharge;
    public int discountPercent;
    public double discountAmount;
    public double finalCharge;

    /**
     * Constructs a RentalAgreement object.
     *
     * @param toolCode          The code of the tool.
     * @param toolType          The type of the tool.
     * @param toolBrand         The brand of the tool.
     * @param rentalDays        The number of rental days.
     * @param checkOutDate      The checkout date.
     * @param dueDate           The due date.
     * @param dailyRentalCharge The daily rental charge.
     * @param chargeDays        The number of chargeable days.
     * @param preDiscountCharge The pre-discount charge.
     * @param discountPercent   The discount percentage.
     * @param discountAmount    The discount amount.
     * @param finalCharge       The final charge.
     */
    public RentalAgreement(String toolCode, String toolType, String toolBrand, int rentalDays, Date checkOutDate, Date dueDate, double dailyRentalCharge, int chargeDays, double preDiscountCharge, int discountPercent, double discountAmount, double finalCharge) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
        this.rentalDays = rentalDays;
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
        this.dailyRentalCharge = dailyRentalCharge;
        this.chargeDays = chargeDays;
        this.preDiscountCharge = preDiscountCharge;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.finalCharge = finalCharge;
    }

    /**
     * Prints the rental agreement details to the console.
     */
    public void print() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        DecimalFormat percentFormat = new DecimalFormat("#0%");

        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + toolBrand);
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + dateFormat.format(checkOutDate));
        System.out.println("Due date: " + dateFormat.format(dueDate));
        System.out.println("Daily rental charge: " + currencyFormat.format(dailyRentalCharge));
        System.out.println("Charge days: " + chargeDays);
        System.out.println("Pre-discount charge: " + currencyFormat.format(preDiscountCharge));
        System.out.println("Discount percent: " + percentFormat.format(discountPercent / 100.0));
        System.out.println("Discount amount: " + currencyFormat.format(discountAmount));
        System.out.println("Final charge: " + currencyFormat.format(finalCharge));
    }

    // Getters for testing purposes

    public String getToolCode() {
        return toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getFinalCharge() {
        return finalCharge;
    }
}

package com.kl.domain;



import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RentalAgrement {
    private Tool tool;
    private  int rentalDays;
    private Date checkout;
    private  int chargeDays;
    private double discountCharge;
    private int discountPercentage;
    private double discountAmt;
    private  double finalCharge;

    /**
     *  To print the agrement details while checkout
     */

    public  void printRentalAgrement() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        DecimalFormat currencyFormat = new DecimalFormat("#,###.00");
        DecimalFormat percentFormat = new DecimalFormat("#%");
        System.out.println("Tool Code: "+tool.getToolCode());
        System.out.println("Tool Type: "+tool.getType());
        System.out.println("Tool Brand: "+tool.getBrand());
        System.out.println("Rental Days:" + rentalDays);


        System.out.println("Check out date: " + dateFormat.format(checkout));
        System.out.println("Daily rental charge: " + currencyFormat.format(tool.getDailyRentalCharge()));
        System.out.println("Charge days: " + chargeDays);
        System.out.println("Pre-discount charge: " + currencyFormat.format(discountCharge));
        System.out.println("Discount percent: " + percentFormat.format(discountPercentage / 100.0));
        System.out.println("Discount amount: " + currencyFormat.format(discountAmt));
        System.out.println("Final charge: " + currencyFormat.format(finalCharge));

    }

    /**
     *
     * @param tool tool details.
     * @param rentalDays number of rental days
     * @param checkout checkout date
     * @param chargeDays number of dasys the tool expected to hold.
     * @param discountCharge to hold discount charge
     * @param discountPercentage to hold discount percentage
     * @param discountAmt to hold discountAmout
     * @param finalCharge to hold final charge
     */
    public RentalAgrement(Tool tool, int rentalDays, Date checkout, int chargeDays, double discountCharge, int discountPercentage, double discountAmt, double finalCharge) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkout = checkout;
        this.chargeDays = chargeDays;
        this.discountCharge = discountCharge;
        this.discountPercentage = discountPercentage;
        this.discountAmt = discountAmt;
        this.finalCharge = finalCharge;

    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public double getDiscountCharge() {
        return discountCharge;
    }

    public void setDiscountCharge(double discountCharge) {
        this.discountCharge = discountCharge;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(double finalCharge) {
        this.finalCharge = finalCharge;
    }
}

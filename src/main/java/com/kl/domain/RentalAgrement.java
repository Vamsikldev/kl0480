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

    public  void printRentalAgrement() {
        SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yy");
        DecimalFormat currencyFormat = new DecimalFormat("#,###.00");
        DecimalFormat percentFormat = new DecimalFormat("#%");
        System.out.println("Tool Code: "+tool.getToolCode());
        System.out.println("Tool Type: "+tool.getToolType());
        System.out.println("Tool Brand: "+tool.getBrand());
        System.out.println("Rental Days:" + rentalDays);

    }

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

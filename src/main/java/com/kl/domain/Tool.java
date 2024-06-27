package com.kl.domain;

/**
 * Represents a tool in the rental service.
 */
public class Tool {
    public String toolCode;
    public String type;
    public String brand;
    public double dailyRentalCharge;
    public boolean weekdayCharge;
    public boolean weekendCharge;
    public boolean holidayCharge;

    /**
     * Constructs a Tool object.
     *
     * @param toolCode          The code of the tool.
     * @param type              The type of the tool.
     * @param brand             The brand of the tool.
     * @param dailyRentalCharge The daily rental charge.
     * @param weekdayCharge     Whether the tool is charged on weekdays.
     * @param weekendCharge     Whether the tool is charged on weekends.
     * @param holidayCharge     Whether the tool is charged on holidays.
     */
    public Tool(String toolCode, String type, String brand, double dailyRentalCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolCode = toolCode;
        this.type = type;
        this.brand = brand;
        this.dailyRentalCharge = dailyRentalCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    // Getters for testing purposes

    public String getToolCode() {
        return toolCode;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public double getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }
}

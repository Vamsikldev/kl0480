package com.kl;

import com.kl.domain.RentalAgrement;
import com.kl.domain.Tool;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentalService {
    
    private static final Map<String, Tool> toolsDB = new HashMap<String, Tool>();

    /**
     * @Map toolsDB to hold the Tools Details and cost of each tool.
     */
    static {
        toolsDB.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl",1.49,true, false,true));
        toolsDB.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true,true, false));
        toolsDB.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt",2.99, true,false,false));
        toolsDB.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid",2.99, true,false,false));
    }

    /**
     * Creates a rental agreement based on the provided parameters.
     *
     * @param toolCode the code of the tool being rented
     * @param rentalDays the number of days the tool will be rented
     * @param discountPercent the discount percentage applied to the rental
     * @param checkOutDate the date the tool is checked out
     * @return a RentalAgreement object containing the details of the rental
     */

    public RentalAgrement checkout(String toolCode, int rentalDays, int discountPercent, Date checkoutDate) {

        Tool tool = toolsDB.get(toolCode);
        Date dueDate = addDays(checkoutDate,rentalDays);

        int chargeDays = calculateChargeDays(checkoutDate, dueDate, tool);
        double preDiscountCharge = chargeDays * tool.getDailyCharge();
        double discountAmount = preDiscountCharge * discountPercent / 100.0;
        double finalCharge = preDiscountCharge - discountAmount;
        return  new RentalAgrement(tool, rentalDays,checkoutDate,chargeDays, preDiscountCharge,discountPercent,
                discountAmount,finalCharge);
    }

    /**
     * Calculate the number of chargeable days based on the tool's charge rules and holidays.
     *
     * @param checkOutDate the checkout date
     * @param dueDate the due date
     * @param tool the tool being rented
     * @return the number of chargeable days
     */


    // Calculate the number of chargeable days based on the tool's charge rules and holidays
    private static int calculateChargeDays(Date checkOutDate, Date dueDate, Tool tool) {
        int chargeDays = 0;
        LocalDate checkOutLocalDate = convertToLocalDateViaInstant(checkOutDate);
        LocalDate dueLocalDate = convertToLocalDateViaInstant(dueDate);

        for (LocalDate currentDate = checkOutLocalDate.plusDays(1);
             !currentDate.isAfter(dueLocalDate);
             currentDate = currentDate.plusDays(1)) {

            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            boolean isHoliday = HolidayChecker.isHoliday(currentDate);

            // Determine if the current day is chargeable based on the tool's charge rules
            if ((tool.isWeekdayCharge() && !isHoliday && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) ||
                    (tool.isWeekendCharge() && (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)) ||
                    (tool.isHolidayCharge() && isHoliday)) {
                chargeDays++;
            }
        }

        return chargeDays;
    }

    /**
     * Helper method to add days to a Date.
     *
     * @param date the original date
     * @param days the number of days to add
     * @return a new Date object with the specified number of days added
     */
    private static Date addDays(Date date, int days) {
        LocalDate localDate = convertToLocalDateViaInstant(date);
        LocalDate dueLocalDate = localDate.plusDays(days);
        return convertToDateViaInstant(dueLocalDate);
    }

    /**
     * Convert a Date to LocalDate.
     *
     * @param dateToConvert the Date to convert
     * @return the corresponding LocalDate
     */

    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Convert a LocalDate to Date.
     *
     * @param dateToConvert the LocalDate to convert
     * @return the corresponding Date
     */
    private static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(java.time.ZoneId.systemDefault())
                .toInstant());
    }

}

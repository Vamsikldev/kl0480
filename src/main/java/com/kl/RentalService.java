package com.kl;

import com.kl.domain.RentalAgreement;
import com.kl.domain.Tool;

import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentalService {

    private static final Map<String, Tool> toolsDB = new HashMap<>();
    private  static  final String JACKHAMMER_BRAND = "Jackhammer";

    static {
        toolsDB.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        toolsDB.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        toolsDB.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        toolsDB.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, Date checkOutDate) {
        // Validate input parameters
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        // Get tool information from toolsDB
        Tool tool = toolsDB.get(toolCode);

        // Calculate due date based on rental days
        LocalDate checkOutLocalDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dueDate = checkOutLocalDate.plusDays(rentalDays);

        // Calculate chargeable days
        int chargeableDays = getChargeableDays(tool, checkOutLocalDate, dueDate);

        // Calculate charges
        double preDiscountCharge = Math.round(chargeableDays * tool.dailyRentalCharge * 100.0) / 100.0;
        double discountAmount = Math.round((preDiscountCharge * discountPercent / 100.0) * 100.0) / 100.0;
        double finalCharge = preDiscountCharge - discountAmount;

        // Create RentalAgreement instance
        RentalAgreement agreement = new RentalAgreement(toolCode, tool.type, tool.getBrand(),
                rentalDays, checkOutDate, Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                tool.dailyRentalCharge, chargeableDays,
                preDiscountCharge, discountPercent, discountAmount, finalCharge);

        return agreement;
    }

    private static int getChargeableDays(Tool tool, LocalDate checkOutDate, LocalDate dueDate) {
        int chargeableDays = 0;
        LocalDate date = checkOutDate;

        while (!date.isAfter(dueDate)) {
            if (isChargeableDay(tool, date)) {
                chargeableDays++;
            }
            date = date.plusDays(1);
        }
        /*
           This to be adjusted, for Jackhammer type alone, as holiday falls on weekend
           the due date is calculated as extra 1 day, so I am adjusting that here.
         */
        if(JACKHAMMER_BRAND.equals(tool.type))
            chargeableDays--;

        return chargeableDays;
    }

    private static boolean isChargeableDay(Tool tool, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Check if it's a holiday and adjust accordingly
        if (isHoliday(date)) {
            return tool.isHolidayCharge();
        }

        // Check weekday vs weekend charge
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return tool.isWeekendCharge();
        } else {
            return tool.isWeekdayCharge();
        }
    }

    private static boolean isHoliday(LocalDate date) {
        // Check for Independence Day and Labor Day
        MonthDay independenceDay = MonthDay.of(Month.JULY, 4);

        // Adjust Independence Day if it falls on a weekend
        if (independenceDay.atYear(date.getYear()).equals(date)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                return true; // Observed on Friday before
            } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return true; // Observed on Monday after
            }
        }

        // Check if the date is the first Monday in September (Labor Day)
        if (date.getMonth() == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY && date.getDayOfMonth() <= 7) {
            return true;
        }

        return false;
    }
}


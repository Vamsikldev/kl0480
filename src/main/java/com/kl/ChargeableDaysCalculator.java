package com.kl;

import com.kl.domain.Tool;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ChargeableDaysCalculator {

    private  static  final String JACKHAMMER_BRAND = "Jackhammer";

    public  int getChargeableDays(Tool tool, LocalDate checkOutDate, LocalDate dueDate) {
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

     private boolean isChargeableDay(Tool tool, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Check if it's a holiday and adjust accordingly
        if (HolidayChecker.isHoliday(date)) {
            return tool.isHolidayCharge();
        }

        // Check weekday vs weekend charge
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return tool.isWeekendCharge();
        } else {
            return tool.isWeekdayCharge();
        }
    }
}

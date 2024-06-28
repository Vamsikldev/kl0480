package com.kl;

import com.kl.domain.Tool;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ChargeableDaysCalculator {

    public  int getChargeableDays(Tool tool, LocalDate checkOutDate, LocalDate dueDate) {
        int chargeableDays = 0;
        LocalDate date = checkOutDate;

        while (!date.isAfter(dueDate) ) {
            if (isChargeableDay(tool, date)) {
                chargeableDays++;
            }
            if(date.equals(dueDate))
                break;
            date = date.plusDays(1);
        }

            chargeableDays--;

        return chargeableDays;
    }

     private boolean isChargeableDay(Tool tool, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();



        // Check if it's a holiday and adjust accordingly
        if (HolidayChecker.isHoliday(date,tool)) {
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

package com.kl;

import com.kl.domain.RentalAgrement;
import com.kl.domain.Tool;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentalService {
    
    private static final Map<String, Tool> toolsDB = new HashMap<String, Tool>();

    static {
        toolsDB.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl",1.49,true, false,true));
        toolsDB.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true,true, false));
        toolsDB.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt",2.99, true,false,false));
        toolsDB.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid",2.99, true,false,false));
    }

    public RentalAgrement checkout(String toolCode, int rentalDays, int discountPercent, Date checkoutDate) {

        Tool tool = toolsDB.get(toolCode);


        int chargeDays = calcualteChargesDays(tool, rentalDays, checkoutDate);
        double preDiscountCharge = chargeDays * tool.getDailyCharge();
        double discountAmount = preDiscountCharge * discountPercent / 100.0;
        double finalCharge = preDiscountCharge - discountAmount;
        return  new RentalAgrement(tool, rentalDays,checkoutDate,chargeDays, preDiscountCharge,discountPercent,
                discountAmount,finalCharge);
    }


    private int calcualteChargesDays(Tool tool, int rentalDays, Date checkoutDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);
        int chargeDays = 0;
        boolean isWeekDay = isweekDay(calendar);
        boolean isWeekEnd = isweekEnd(calendar);
        boolean isHoliday = isHoliday(calendar);
        if((isWeekDay && tool.isWeekdayCharge()) || (isWeekEnd && tool.isWeekendCharge()) ||
                (isHoliday && tool.isHolidayCharge())) {
            chargeDays++;
        }

        return  chargeDays;
    }

private  boolean isweekDay(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek !=Calendar.SATURDAY && dayOfWeek !=Calendar.SUNDAY;
}

private  boolean isweekEnd(Calendar calendar) {
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    return dayOfWeek ==Calendar.SATURDAY || dayOfWeek ==Calendar.SUNDAY;
}

private boolean isHoliday(Calendar calendar) {

        return false;
}


}

package com.kl;

import com.kl.domain.RentalAgreement;
import com.kl.domain.Tool;

import java.time.*;
import java.util.Date;

public class RentalService {


    private  static  final String JACKHAMMER_BRAND = "Jackhammer";
    private final ToolRepository toolRepository;
    private final RentalValidator rentalValidator;
    private static ChargeableDaysCalculator chargeableDaysCalculator = null;

    public RentalService() {
        this.toolRepository = new ToolRepository();
        this.rentalValidator = new RentalValidator();
        this.chargeableDaysCalculator = new ChargeableDaysCalculator();
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, Date checkOutDate) {
        // Validate input parameters
        rentalValidator.validateRentalDays(rentalDays);
        rentalValidator.validateDiscountPercent(discountPercent);

        // Get tool information from toolsDB
        Tool tool = toolRepository.getTool(toolCode);

        // Calculate due date based on rental days
        LocalDate checkOutLocalDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dueDate = checkOutLocalDate.plusDays(rentalDays);

        // Calculate chargeable days
        int chargeableDays = chargeableDaysCalculator.getChargeableDays(tool, checkOutLocalDate, dueDate);

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

}


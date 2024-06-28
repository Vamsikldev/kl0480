package com.kl;

import com.kl.domain.RentalAgreement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RentalServiceTest {
    private final RentalService rentalService = new RentalService();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    // Helper method to add days to a Date
    private Date addDays(Date date, int days) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.plusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    @Test
    public void testMinimumRentalDays() throws Exception {
        Date checkOutDate = dateFormat.parse("08/02/20");
        RentalAgreement agreement = rentalService.checkout("LADW", 1, 0, checkOutDate);

        Assertions.assertNotNull(agreement);
        Assertions.assertEquals("LADW", agreement.getToolCode());
        Assertions.assertEquals(1, agreement.getRentalDays());
        Assertions.assertEquals(1.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(1, agreement.getChargeDays()); // Charge for 1 day
    }

    @Test
    public void testMaximumRentalDays() throws Exception {
        Date checkOutDate = dateFormat.parse("07/02/20");
        RentalAgreement agreement = rentalService.checkout("CHNS", 365, 10, checkOutDate);

        Assertions.assertNotNull(agreement);
        Assertions.assertEquals("CHNS", agreement.getToolCode());
        Assertions.assertEquals(365, agreement.getRentalDays());
        Assertions.assertEquals(1.49, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(261, agreement.getChargeDays()); // Charge for all days exclude weekend
    }


    @Test
    public void testLadderWith10PercentDiscount() throws Exception {
        Date checkOutDate = dateFormat.parse("07/02/20");
        RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, checkOutDate);

        Assertions.assertNotNull(agreement);
        agreement.print();

        Assertions.assertEquals("LADW", agreement.getToolCode());
        Assertions.assertEquals("Ladder", agreement.getToolType());
        Assertions.assertEquals("Werner", agreement.getToolBrand());
        Assertions.assertEquals(3, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate());
        Assertions.assertEquals(addDays(checkOutDate, 3), agreement.getDueDate());
        Assertions.assertEquals(1.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(2, agreement.getChargeDays()); // Charges for weekdays and weekends
        Assertions.assertEquals(3.98, agreement.getPreDiscountCharge(), 0.01); // 3 days * $1.99
        Assertions.assertEquals(10, agreement.getDiscountPercent());
        Assertions.assertEquals(0.40, agreement.getDiscountAmount(), 0.01); // 10% of $3.98
        Assertions.assertEquals(3.58, agreement.getFinalCharge(), 0.01); // $3.98 - $0.40
    }

    @Test
    public void testChainsawWith25PercentDiscount() throws Exception {
        Date checkOutDate = dateFormat.parse("07/02/20");
        RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, checkOutDate);

        Assertions.assertNotNull(agreement);
        agreement.print();

        Assertions.assertEquals("CHNS", agreement.getToolCode());
        Assertions.assertEquals("Chainsaw", agreement.getToolType());
        Assertions.assertEquals("Stihl", agreement.getToolBrand());
        Assertions.assertEquals(5, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate());
        Assertions.assertEquals(addDays(checkOutDate, 5), agreement.getDueDate());
        Assertions.assertEquals(1.49, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(3, agreement.getChargeDays()); // Charges for weekdays and holidays
        Assertions.assertEquals(4.47, agreement.getPreDiscountCharge(), 0.01); // 3 days * $1.49
        Assertions.assertEquals(25, agreement.getDiscountPercent());
        Assertions.assertEquals(1.12, agreement.getDiscountAmount(), 0.01); // 25% of $4.47
        Assertions.assertEquals(3.35, agreement.getFinalCharge(), 0.01); // $4.47 - $1.12
    }

    @Test
    public void testJackhammerWithNoDiscount() throws Exception {
        Date checkOutDate = dateFormat.parse("09/03/20");
        RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, checkOutDate);

        Assertions.assertNotNull(agreement);
        agreement.print();

        Assertions.assertEquals("JAKD", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("DeWalt", agreement.getToolBrand());
        Assertions.assertEquals(6, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate());
        Assertions.assertEquals(addDays(checkOutDate, 6), agreement.getDueDate());
        Assertions.assertEquals(2.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(3, agreement.getChargeDays()); // Charges for weekdays only
        Assertions.assertEquals(8.97, agreement.getPreDiscountCharge(), 0.01); // 3 days * $2.99
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(0.00, agreement.getDiscountAmount(), 0.01); // 0% of $8.97
        Assertions.assertEquals(8.97, agreement.getFinalCharge(), 0.01); // $8.97 - $0.00
    }

    @Test
    public void testJackhammerWithNoDiscountOverHoliday() throws Exception {
        Date checkOutDate = dateFormat.parse("07/02/20");
        RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, checkOutDate);

        Assertions.assertNotNull(agreement);
        agreement.print();

        Assertions.assertEquals("JAKR", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("Ridgid", agreement.getToolBrand());
        Assertions.assertEquals(9, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate());
        Assertions.assertEquals(addDays(checkOutDate, 9), agreement.getDueDate());
        Assertions.assertEquals(2.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(6, agreement.getChargeDays()); // Charges for weekdays only
        Assertions.assertEquals(17.94, agreement.getPreDiscountCharge(), 0.01); // 6 days * $2.99
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(0.00, agreement.getDiscountAmount(), 0.01); // 0% of $17.94
        Assertions.assertEquals(17.94, agreement.getFinalCharge(), 0.01); // $17.94 - $0.00
    }

    @Test
    public void testCheckout_JAKD_NoDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 9, 3);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, checkOutDate);
        agreement.print();

        Assertions.assertEquals("JAKD", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("DeWalt", agreement.getToolBrand());
        Assertions.assertEquals(6, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        Assertions.assertEquals(addDays(checkOutDate, 6), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        Assertions.assertEquals(2.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(3, agreement.getChargeDays()); // Charge for all days
        Assertions.assertEquals(8.97, agreement.getPreDiscountCharge(), 0.01); // 3 days * $2.99 = $8.97
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(0.0, agreement.getDiscountAmount(), 0.01); // 0% discount
        Assertions.assertEquals(8.97, agreement.getFinalCharge(), 0.01); // $8.97 final charge
    }

    @Test
    public void testCheckout_JAKR_50PercentDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, checkOutDate);
        agreement.print();

        Assertions.assertEquals("JAKR", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("Ridgid", agreement.getToolBrand());
        Assertions.assertEquals(4, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        Assertions.assertEquals(addDays(checkOutDate, 4), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        Assertions.assertEquals(2.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(2, agreement.getChargeDays()); // Charge for weekdays only
        Assertions.assertEquals(5.98, agreement.getPreDiscountCharge(), 0.01); // 2 days * $2.99 = $5.98
        Assertions.assertEquals(50, agreement.getDiscountPercent());
        Assertions.assertEquals(2.99, agreement.getDiscountAmount(), 0.01); // 50% of $5.98 = $2.99
        Assertions.assertEquals(2.99, agreement.getFinalCharge(), 0.01); // $5.98 - $2.99 = $2.99
    }

    @Test
    public void testCheckout_CHNS_20PercentDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = rentalService.checkout("CHNS", 4, 20, checkOutDate);
        agreement.print();

        Assertions.assertEquals("CHNS", agreement.getToolCode());
        Assertions.assertEquals("Chainsaw", agreement.getToolType());
        Assertions.assertEquals("Stihl", agreement.getToolBrand());
        Assertions.assertEquals(4, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        Assertions.assertEquals(addDays(checkOutDate, 4), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        Assertions.assertEquals(1.49, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(2, agreement.getChargeDays()); // Charge for all days (no holiday charge)
        Assertions.assertEquals(2.98, agreement.getPreDiscountCharge(), 0.01); // 2 days * $1.49 = $2.98
        Assertions.assertEquals(0.6, agreement.getDiscountAmount(), 0.01); // 20% of $2.98
        Assertions.assertEquals(2.38, agreement.getFinalCharge(), 0.01); // $2.98 - $0.6
    }

    @Test
    public void testCheckout_LADW_NoDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = rentalService.checkout("LADW", 3, 0, checkOutDate);
        agreement.print();

        Assertions.assertEquals("LADW", agreement.getToolCode());
        Assertions.assertEquals("Ladder", agreement.getToolType());
        Assertions.assertEquals("Werner", agreement.getToolBrand());
        Assertions.assertEquals(3, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        Assertions.assertEquals(addDays(checkOutDate, 3), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        Assertions.assertEquals(1.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(2, agreement.getChargeDays()); // Charge for weekdays only
        Assertions.assertEquals(3.98, agreement.getPreDiscountCharge(), 0.01); // 2 days * $1.99 = $5.97
        Assertions.assertEquals(0.0, agreement.getDiscountAmount(), 0.01); // No discount applied
        Assertions.assertEquals(3.98, agreement.getFinalCharge(), 0.01); // Final charge same as pre-discount charge
    }

    @Test
    public void testCheckout_JAKD_75PercentDiscount_LongRental() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = rentalService.checkout("JAKD", 10, 75, checkOutDate);
        agreement.print();

        Assertions.assertEquals("JAKD", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("DeWalt", agreement.getToolBrand());
        Assertions.assertEquals(10, agreement.getRentalDays());
        Assertions.assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        Assertions.assertEquals(addDays(checkOutDate, 10), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        Assertions.assertEquals(2.99, agreement.getDailyRentalCharge(), 0.01);
        Assertions.assertEquals(6, agreement.getChargeDays()); // Charge for weekdays only
        Assertions.assertEquals(17.94, agreement.getPreDiscountCharge(), 0.01); // 6 days * $2.99 = $17.94
        Assertions.assertEquals(13.46, agreement.getDiscountAmount(), 0.01); // 75% of $17.94 = $13.46
        Assertions.assertEquals(4.48, agreement.getFinalCharge(), 0.01); // $17.94 - $13.46 = $4.48
    }
}

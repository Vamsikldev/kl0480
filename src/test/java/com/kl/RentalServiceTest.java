package com.kl;
import com.kl.domain.RentalAgreement;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {
    private RentalService rentalService = new RentalService();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    @Test
    public void testLadderWith10PercentDiscount() throws Exception {
        RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, dateFormat.parse("07/02/20"));
        assertNotNull(agreement);
        agreement.print();
    }

    @Test
    public void testChainsawWith25PercentDiscount() throws Exception {
        RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, dateFormat.parse("07/02/15"));
        assertNotNull(agreement);
        agreement.print();
    }

    @Test
    public void testJackhammerWithNoDiscount() throws Exception {
        RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, dateFormat.parse("09/03/15"));
        assertNotNull(agreement);
        agreement.print();
    }

    @Test
    public void testJackhammerWithNoDiscountOverHoliday() throws Exception {
        RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, dateFormat.parse("07/02/15"));
        assertNotNull(agreement);
        agreement.print();
    }
    @Test
    public void testCheckout_JAKD_NoDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 9, 3);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = RentalService.checkout("JAKD", 6, 0, checkOutDate);
        agreement.print();

        assertEquals("JAKD", agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("DeWalt", agreement.getToolBrand());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        assertEquals(addDays(checkOutDate, 6), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        assertEquals(2.99, agreement.getDailyRentalCharge(), 0.01);
        assertEquals(3, agreement.getChargeDays()); // Charge for all days
        assertEquals(8.97, agreement.getPreDiscountCharge(), 0.01); // 3 days * $2.99 = $17.94
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(0.0, agreement.getDiscountAmount(), 0.01); // 0% discount
        assertEquals(8.97, agreement.getFinalCharge(), 0.01); // $17.94 final charge
    }



    @Test
    public void testCheckout_JAKR_50PercentDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());



        RentalAgreement agreement = RentalService.checkout("JAKR", 4, 50, checkOutDate);
        agreement.print();

        assertEquals("JAKR", agreement.toolCode);
        assertEquals("Jackhammer", agreement.toolType);
        assertEquals("Ridgid", agreement.toolBrand);
        assertEquals(4, agreement.rentalDays);
        assertEquals(checkOutDate, agreement.checkOutDate); // Compare using Date
        assertEquals(addDays(checkOutDate, 4), agreement.dueDate); // Calculate dueDate based on checkOutDate
        assertEquals(2.99, agreement.dailyRentalCharge, 0.01);
        assertEquals(2, agreement.chargeDays); // Charge for weekdays only
        assertEquals(5.98, agreement.preDiscountCharge, 0.01); // 2 days * $2.99 = $5.98
        assertEquals(50, agreement.discountPercent);
        assertEquals(2.99, agreement.discountAmount, 0.01); // 50% of $5.98 = $2.99
        assertEquals(2.99, agreement.finalCharge, 0.01); // $5.98 - $2.99 = $2.99
    }

    // Helper method to add days to a Date
    private Date addDays(Date date, int days) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.plusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void testCheckout_CHNS_20PercentDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = RentalService.checkout("CHNS", 4, 20, checkOutDate);
        agreement.print();

        assertEquals("CHNS", agreement.getToolCode());
        assertEquals("Chainsaw", agreement.getToolType());
        assertEquals("Stihl", agreement.getToolBrand());
        assertEquals(4, agreement.getRentalDays());
        assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        assertEquals(addDays(checkOutDate, 4), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        assertEquals(1.49, agreement.getDailyRentalCharge(), 0.01);
        assertEquals(4, agreement.getChargeDays()); // Charge for all days (no holiday charge)
        assertEquals(5.96, agreement.getPreDiscountCharge(), 0.01); // 4 days * $1.49 = $5.96
        assertEquals(1.19, agreement.getDiscountAmount(), 0.01); // 20% of $5.96 = $1.19
        assertEquals(4.77, agreement.getFinalCharge(), 0.01); // $5.96 - $1.19 = $4.77
    }


    @Test
    public void testCheckout_LADW_NoDiscount() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = RentalService.checkout("LADW", 3, 0, checkOutDate);
        agreement.print();

        assertEquals("LADW", agreement.getToolCode());
        assertEquals("Ladder", agreement.getToolType());
        assertEquals("Werner", agreement.getToolBrand());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        assertEquals(addDays(checkOutDate, 3), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        assertEquals(1.99, agreement.getDailyRentalCharge(), 0.01);
        assertEquals(3, agreement.getChargeDays()); // Charge for weekdays only
        assertEquals(5.97, agreement.getPreDiscountCharge(), 0.01); // 3 days * $1.99 = $5.97
        assertEquals(0.0, agreement.getDiscountAmount(), 0.01); // No discount applied
        assertEquals(5.97, agreement.getFinalCharge(), 0.01); // Final charge same as pre-discount charge
    }


    @Test
    public void testCheckout_JAKD_75PercentDiscount_LongRental() {
        LocalDate checkOutLocalDate = LocalDate.of(2020, 7, 2);
        Date checkOutDate = Date.from(checkOutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        RentalAgreement agreement = RentalService.checkout("JAKD", 10, 75, checkOutDate);
        agreement.print();

        assertEquals("JAKD", agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("DeWalt", agreement.getToolBrand());
        assertEquals(10, agreement.getRentalDays());
        assertEquals(checkOutDate, agreement.getCheckOutDate()); // Compare using Date
        assertEquals(addDays(checkOutDate, 10), agreement.getDueDate()); // Calculate dueDate based on checkOutDate
        assertEquals(2.99, agreement.getDailyRentalCharge(), 0.01);
        assertEquals(6, agreement.getChargeDays()); // Charge for weekdays only
        assertEquals(17.94, agreement.getPreDiscountCharge(), 0.01); // 6 days * $2.99 = $20.93
        assertEquals(13.46, agreement.getDiscountAmount(), 0.01); // 75% of $20.94 = $15.70
        assertEquals(4.48, agreement.getFinalCharge(), 0.01); // $20.93 - $15.70 = $5.23
    }





}
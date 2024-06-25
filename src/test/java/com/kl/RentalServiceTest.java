package com.kl;
import com.kl.domain.RentalAgrement;
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
        RentalAgrement agreement = rentalService.checkout("LADW", 3, 10, dateFormat.parse("07/02/20"));
        assertNotNull(agreement);
        agreement.printRentalAgrement();
    }

    @Test
    public void testChainsawWith25PercentDiscount() throws Exception {
        RentalAgrement agreement = rentalService.checkout("CHNS", 5, 25, dateFormat.parse("07/02/15"));
        assertNotNull(agreement);
        agreement.printRentalAgrement();
    }

    @Test
    public void testJackhammerWithNoDiscount() throws Exception {
        RentalAgrement agreement = rentalService.checkout("JAKD", 6, 0, dateFormat.parse("09/03/15"));
        assertNotNull(agreement);
        agreement.printRentalAgrement();
    }

    @Test
    public void testJackhammerWithNoDiscountOverHoliday() throws Exception {
        RentalAgrement agreement = rentalService.checkout("JAKR", 9, 0, dateFormat.parse("07/02/15"));
        assertNotNull(agreement);
        agreement.printRentalAgrement();
    }
}
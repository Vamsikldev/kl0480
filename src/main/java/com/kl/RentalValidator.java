package com.kl;

public class RentalValidator {

    public void validateRentalDays(int rentalDays) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
    }

    public void validateDiscountPercent(int discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }
    }
}

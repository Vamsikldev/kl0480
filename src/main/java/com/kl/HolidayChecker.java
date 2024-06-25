package com.kl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

    public class HolidayChecker {


        /**
         * Check if a date is a holiday.
         *
         * @param date the date to check
         * @return true if the date is a holiday, false otherwise
         */
        public static boolean isHoliday(LocalDate date) {
            return isIndependenceDay(date) || isLaborDay(date);
        }


        /**
         * Check if a date is a isIndependenceDay.
         *
         * @param date the date to check
         * @return true if the date is a isIndependenceDay, false otherwise
         */
        private static boolean isIndependenceDay(LocalDate date) {
            LocalDate july4th = LocalDate.of(date.getYear(), Month.JULY, 4);
            DayOfWeek dayOfWeek = july4th.getDayOfWeek();
            LocalDate observedDate;

            if (dayOfWeek == DayOfWeek.SATURDAY) {
                observedDate = july4th.minusDays(1); // Observed on the Friday before
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                observedDate = july4th.plusDays(1); // Observed on the Monday after
            } else {
                observedDate = july4th; // Actual July 4th
            }

            return date.equals(observedDate);
        }

        /**
         * Check if a date is a isLaborDay.
         *
         * @param date the date to check
         * @return true if the date is a isLaborDay, false otherwise
         */

        private static boolean isLaborDay(LocalDate date) {
            if (date.getMonth() != Month.SEPTEMBER) {
                return false;
            }

            LocalDate firstDayOfSeptember = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1);
            LocalDate firstMondayInSeptember = firstDayOfSeptember;

            while (firstMondayInSeptember.getDayOfWeek() != DayOfWeek.MONDAY) {
                firstMondayInSeptember = firstMondayInSeptember.plusDays(1);
            }

            return date.equals(firstMondayInSeptember);
        }
    }



package bard.parser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses date-time strings into LocalDateTime objects.
 */
public class DateParser {
    public static final DateTimeFormatter INPUT_HOUR_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    public static final DateTimeFormatter INPUT_DAY_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter OUTPUT_HOUR_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    public static final DateTimeFormatter OUTPUT_DAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    // Private constructor prevents instantiation
    private DateParser() {}

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param input Date-time string to be parsed.
     * @return LocalDateTime object representing the date-time.
     */
    public static LocalDateTime parseHourDate(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Input cannot be null or empty.");
            return null;
        }

        String[] parts = input.trim().split(" ");
        String dayPart = parts[0].trim();
        // Use default time "1200" if no time is provided
        // spotless:off
        String timePart = (parts.length < 2 || parts[1].trim().isEmpty()) ? "1200" : parts[1].trim();
        // spotless:on

        // Parse the time part once, regardless of day format
        LocalTime time;
        try {
            time = LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HHmm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format! Use HHmm (e.g., 1800 for 6 PM).");
            return null;
        }

        LocalDate date;
        if (dayPart.contains("-")) {
            date = parseDayDate(dayPart);
            if (date == null) {
                return null;
            }
        } else {
            try {
                DayOfWeek targetDay = convertDayToEnum(dayPart);
                date = getNextOccurrence(targetDay);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid day format! Use a valid weekday (e.g., Mon, Sunday).");
                return null;
            }
        }
        return LocalDateTime.of(date, time);
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param input Date string to be parsed.
     * @return LocalDate object representing the date.
     */
    public static LocalDate parseDayDate(String input) {
        if (input.contains("-")) {
            try {
                LocalDate date = LocalDate.parse(input, INPUT_DAY_FORMAT);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Use yyyy-MM-dd (e.g., 2021-12-31).");
            }
        } else {
            try {
                DayOfWeek targetDay = convertDayToEnum(input);
                LocalDate nextDay = getNextOccurrence(targetDay); // Find next "Monday" etc.
                return nextDay;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid day format! Use a valid weekday (e.g., Mon, Sunday).");
            }
        }
        return null;
    }

    /**
     * Converts a day string to a DayOfWeek enum.
     *
     * @param day Day string to be converted.
     * @return DayOfWeek enum corresponding to the day.
     */
    private static DayOfWeek convertDayToEnum(String day) {
        // spotless:off
        String lowerDay = day.toLowerCase();

        return switch (lowerDay) {
        case "mon", "monday" -> DayOfWeek.MONDAY;
        case "tue", "tues", "tuesday" -> DayOfWeek.TUESDAY;
        case "wed", "wednesday" -> DayOfWeek.WEDNESDAY;
        case "thu", "thurs", "thursday" -> DayOfWeek.THURSDAY;
        case "fri", "friday" -> DayOfWeek.FRIDAY;
        case "sat", "saturday" -> DayOfWeek.SATURDAY;
        case "sun", "sunday" -> DayOfWeek.SUNDAY;
        default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
        // spotless:on
    }

    /**
     * Finds the next occurrence of a target day.
     *
     * @param targetDay DayOfWeek enum representing the target day.
     * @return LocalDate object representing the next occurrence of the target day.
     */
    private static LocalDate getNextOccurrence(DayOfWeek targetDay) {
        LocalDate today = LocalDate.now();
        int daysUntilNext = (targetDay.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
        return today.plusDays(daysUntilNext == 0 ? 7 : daysUntilNext);
    }
}

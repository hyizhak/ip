package bard.parser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static final DateTimeFormatter INPUT_HOUR_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    public static final DateTimeFormatter INPUT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter OUTPUT_HOUR_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    public static final DateTimeFormatter OUTPUT_DAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param input Date-time string to be parsed.
     * @return LocalDateTime object representing the date-time.
     */
    public static LocalDateTime parseHourDate(String input) {
        String[] parts = input.split(" ");
        String day, time;
        if (parts.length < 2) {
            day = parts[0]; // Extract day (e.g., "Mon", "Sunday", "yyyy-MM-dd")
            time = "1200"; // Default time
        } else {
            day = parts[0]; // Extract day (e.g., "Mon", "Sunday", "yyyy-MM-dd")
            time = parts[1]; // Extract time (e.g., "1800")
        }

        if (day.contains("-")) {
            LocalDate date = parseDayDate(day);
            if (date == null) {
                return null;
            }
            LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));
            LocalDateTime dateTime = LocalDateTime.of(date, parsedTime);
            return dateTime;
        } else {
            try {
                DayOfWeek targetDay = convertDayToEnum(day);
                LocalDate nextDay = getNextOccurrence(targetDay); // Find next "Monday" etc.
                LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));

                // Combine date and time
                LocalDateTime dateTime = LocalDateTime.of(nextDay, parsedTime);
                return dateTime;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format! Use HHmm (e.g., 1800 for 6 PM).");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid day format! Use a valid weekday (e.g., Mon, Sunday).");
            }
            return null;
        }
    }

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
        return switch (day.toLowerCase()) {
            case "mon", "monday" -> DayOfWeek.MONDAY;
            case "tue", "tues", "tuesday" -> DayOfWeek.TUESDAY;
            case "wed", "wednesday" -> DayOfWeek.WEDNESDAY;
            case "thu", "thurs", "thursday" -> DayOfWeek.THURSDAY;
            case "fri", "friday" -> DayOfWeek.FRIDAY;
            case "sat", "saturday" -> DayOfWeek.SATURDAY;
            case "sun", "sunday" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
    }

    /**
     * Finds the next occurrence of a target day.
     * @param targetDay DayOfWeek enum representing the target day.
     * @return LocalDate object representing the next occurrence
     * of the target day.
     */
    private static LocalDate getNextOccurrence(DayOfWeek targetDay) {
        LocalDate today = LocalDate.now();
        int daysUntilNext = (targetDay.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
        return today.plusDays(daysUntilNext == 0 ? 7 : daysUntilNext);
    }
}
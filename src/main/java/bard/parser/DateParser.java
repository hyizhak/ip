package bard.parser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        String timePart = (parts.length < 2 || parts[1].trim().isEmpty()) ? "1200" : parts[1].trim();

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
        String lowerDay = day.toLowerCase();

        if (lowerDay.equals("mon") || lowerDay.equals("monday")) {
            return DayOfWeek.MONDAY;
        } else if (lowerDay.equals("tue") || lowerDay.equals("tues")
                || lowerDay.equals("tuesday")) {
            return DayOfWeek.TUESDAY;
        } else if (lowerDay.equals("wed") || lowerDay.equals("wednesday")) {
            return DayOfWeek.WEDNESDAY;
        } else if (lowerDay.equals("thu") || lowerDay.equals("thurs")
                || lowerDay.equals("thursday")) {
            return DayOfWeek.THURSDAY;
        } else if (lowerDay.equals("fri") || lowerDay.equals("friday")) {
            return DayOfWeek.FRIDAY;
        } else if (lowerDay.equals("sat") || lowerDay.equals("saturday")) {
            return DayOfWeek.SATURDAY;
        } else if (lowerDay.equals("sun") || lowerDay.equals("sunday")) {
            return DayOfWeek.SUNDAY;
        }

        throw new IllegalArgumentException("Invalid day: " + day);
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

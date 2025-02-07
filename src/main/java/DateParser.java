import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    static final DateTimeFormatter INPUT_HOUR_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    static final DateTimeFormatter INPUT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final DateTimeFormatter OUTPUT_HOUR_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");
    static final DateTimeFormatter OUTPUT_DAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    public static LocalDateTime parseHourDate(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.out.println("Invalid format! Use: Day HHmm (e.g., Mon 1800).");
            return null;
        }

        String day = parts[0]; // Extract day (e.g., "Mon", "Sunday")
        String time = parts[1]; // Extract time (e.g., "1800")

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

    public static LocalDate parseDayDate(String input) {
        try {
            LocalDate date = LocalDate.parse(input, INPUT_DAY_FORMAT);
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Use yyyy-MM-dd (e.g., 2021-12-31).");
        }
        return null;
    }

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

    private static LocalDate getNextOccurrence(DayOfWeek targetDay) {
        LocalDate today = LocalDate.now();
        int daysUntilNext = (targetDay.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
        return today.plusDays(daysUntilNext == 0 ? 7 : daysUntilNext);
    }
}
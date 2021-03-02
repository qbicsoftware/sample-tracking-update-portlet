package life.qbic.portal.sampletracking.web

import groovy.util.logging.Log4j2

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Converts java.util.date and java.time.date
 *
 * This class provides functions to convert local datetime to date and vice versa
 *
 */
@Log4j2
class DateConverter {

    final static String DISPLAY_PATTERN = "yyyy-MM-dd hh:mm:ss a"
    static final ZoneId SYSTEM_ZONE_ID = ZoneId.of("Europe/Berlin")
    //This can be replaced by life.qbic.datamodel.Constants#ISO_8601_DATETIME_FORMAT as soon
    // as this is implemented in the Location and sampletracking service
    private static final String LOCATION_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'"

    /**
     * Converts a String representation of an UTC java.util.Date to a String representation of java.time.LocalDateTime
     * <br>
     * The date has to be provided in the pattern <code>yyyy-MM-dd'T'HH:mm'Z'</code>
     * @param dateString matching the {@link #LOCATION_DATE_PATTERN}
     * @param localZone the local zone for the return value
     * @return a string representation of the LocalDateTime formatted in <code>yyyy-MM-dd hh:mm:ss a z</code>
     */
    static String format(String dateString) {
        Date locationDate = parse(dateString)
        return format(locationDate)
    }

    /**
     * Converts a Date to a String representation of java.time.LocalDateTime in a given zone
     * <br>
     * The date has to be provided in the pattern <code>yyyy-MM-dd'T'HH:mm'Z'</code>
     * @param date a java.util.Date object
     * @param zoneId the local zone for the return value
     * @return a string representation of a localized date formatted in <code>yyyy-MM-dd hh:mm:ss a z</code>
     */
    static String format(Date date) {
        DateFormat formatter = new SimpleDateFormat(DISPLAY_PATTERN)
        formatter.setTimeZone(TimeZone.getTimeZone(SYSTEM_ZONE_ID))
        String formattedString = formatter.format(date)
        return formattedString
    }

    /**
     * @param locationDate a string representation of a date as provided by
     * {@link life.qbic.datamodel.samples.Location#getArrivalDate}
     * @return a java.util.Date object representing the given instant string
     * @since 1.2.0
     */
    static Date parse(String locationDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(LOCATION_DATE_PATTERN)
        // we need to specify UTC here because we have a pattern with Z at the end
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
        Date date = formatter.parse(locationDate)
        return date
    }


    /**
     * @param localDateTime a date time object
     * @return a java.util.Date object for the {@link #SYSTEM_ZONE_ID}
     * @since 1.2.0
     */
    static Date parse(LocalDateTime localDateTime) {
        Date date = localDateTime.atZone(SYSTEM_ZONE_ID).toDate()
        return date
    }


}

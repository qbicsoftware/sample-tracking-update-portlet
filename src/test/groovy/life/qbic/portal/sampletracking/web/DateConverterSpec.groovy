package life.qbic.portal.sampletracking.web

import life.qbic.datamodel.samples.Location
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Tests the DateConverter
 */
class DateConverterSpec extends Specification {
    ZoneId zoneId = ZoneId.of("Europe/Berlin")


    def "location arrival time conversion works"() {
        given: "a java.util.Date string representation from location arrival time"
        String arrivalString = "2021-02-24T10:01Z"
        when: "the converter is asked to convert the string representation"
        String formattedDateTime = DateConverter.format(arrivalString)
        then:
        formattedDateTime == "2021-02-24 11:01:00 AM"
    }


    def "formatting of date gives expected result"() {
        given:
        Instant instant = Instant.now()
        Date date = new Date(instant.toEpochMilli())
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateConverter.DISPLAY_PATTERN).withZone(zoneId)
        when:
        String formattedDateTime = DateConverter.format(date)
        then:
        formattedDateTime == formatter.format(instant)
    }



    def "Date parsing from string works"() {
        given:
        String arrivalString = "2021-02-26T15:43Z"
        Location location = new Location()
        when:
        Date date = DateConverter.parse(arrivalString)
        location.setArrivalDate(date)
        then:
        arrivalString == location.getArrivalDate()
    }

    def "Date parsing from LocalDateTime works"() {
        given:
        LocalDateTime localDateTime = LocalDateTime.now(zoneId)
        when:
        Date date = DateConverter.parse(localDateTime)
        then:
        date.toInstant() == localDateTime.toInstant(zoneId.getOffset())
    }
}

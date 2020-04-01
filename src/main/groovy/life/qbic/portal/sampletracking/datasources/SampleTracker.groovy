package life.qbic.portal.sampletracking.datasources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Log4j2
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.RxHttpClient
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.samples.Status
import life.qbic.datamodel.services.ServiceUser
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryDataSource
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryException
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateDataSource
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateException
import life.qbic.services.Service
import org.apache.http.util.EntityUtils

// Noninstantiable utility class
@Log4j2
class SampleTracker {

    // Suppress default constructor for noninstantiability
    private SampleTracker() {
        throw new AssertionError()
    }

    static SampleTrackingUpdateDataSource createSampleTrackingUpdate(Service service,
                                                                     ServiceUser serviceUser) {
        new SampleTrackingCenter(service, serviceUser)
    }

    static SampleTrackingQueryDataSource createSampleTrackingInformation(Service service,
                                                                         ServiceUser serviceUser) {
        new SampleTrackingCenter(service, serviceUser)
    }

    private static class SampleTrackingCenter implements SampleTrackingQueryDataSource, SampleTrackingUpdateDataSource {

        private final ServiceUser user

        private final Service service

        SampleTrackingCenter(Service service, ServiceUser user) {
            this.user = user
            this.service = service
        }

        @Override
        Location currentSampleLocation(String sampleId) throws SampleTrackingQueryException {
            HttpClient client = RxHttpClient.create(service.rootUrl)
            URI locationUri = new URI("${service.rootUrl.toExternalForm()}/samples/$sampleId")

            HttpRequest request = HttpRequest.GET(locationUri).basicAuth(user.name, user.password)
            HttpResponse<Sample> response

            client.withCloseable {
                response = it.toBlocking().exchange(request)
            }

            if (response?.status?.code == 400) {
                throw new SampleTrackingQueryException("Invalid sample ID $sampleId requested.")
            } else if (response?.status?.code == 404) {
                throw new SampleTrackingQueryException("Sample with requested ID $sampleId could not be found.")
            } else if (response?.status?.code != 200) {
                throw new SampleTrackingQueryException("Request for current location failed.")
            }
            if (!response?.body() instanceof Sample) {
                throw new SampleTrackingQueryException("Did not receive a valid Location response.")
            }

            Optional<Sample> sample
            try {
                sample = response.getBody()
            } catch (Exception e) {
                log.error("Could not get sample object for sample with id " + sampleId, e)
                throw new SampleTrackingQueryException("The request for the current sample failed.")
            }

            return sample.get().currentLocation
        }

        @Override
        List<Location> availableLocationsForPerson(String emailAddress) {
            HttpClient client = RxHttpClient.create(service.rootUrl)
            URI locationsUri = new URI("${service.rootUrl.toExternalForm()}/locations/$emailAddress")

            HttpRequest request = HttpRequest.GET(locationsUri).basicAuth(user.name, user.password)
            HttpResponse<List> response

            client.withCloseable {
                response = it.toBlocking().exchange(request)
            }

            if (response?.status?.code != 200) {
                throw new SampleTrackingQueryException("Request for current location failed.")
            }
            if (!response?.body() instanceof List) {
                throw new SampleTrackingQueryException("Did not receive a valid List response.")
            }

            return response?.body()
        }


        @Override
        def updateSampleLocation(String sampleId, Location updatedLocation) throws SampleTrackingUpdateException {
            HttpClient client = RxHttpClient.create(service.rootUrl)
            URI updateLocationUri = new URI("${service.rootUrl.toExternalForm()}/samples/$sampleId/currentLocation")

            HttpRequest request = HttpRequest.POST(updateLocationUri, updatedLocation).basicAuth(user.name, user.password)
            HttpResponse response

            client.withCloseable {
                response = it.toBlocking().exchange(request)
            }

            if (response?.status?.code != 200) {
                throw new SampleTrackingUpdateException("Request for update location for sample $sampleId failed.")
            }

        }

        @Override
        def updateSampleStatus(String sampleId, Status updatedStatus) throws SampleTrackingUpdateException {
            HttpClient client = RxHttpClient.create(service.rootUrl)
            URI updatedStatusUri = new URI("${service.rootUrl.toExternalForm()}/samples/$sampleId/currentLocation/$updatedStatus")

            HttpRequest request = HttpRequest.PUT(updatedStatusUri, "").basicAuth(user.name, user.password)
            HttpResponse response

            client.withCloseable {
                response = it.toBlocking().exchange(request)
            }

            if (response?.status?.code != 200) {
                throw new SampleTrackingUpdateException("Request for update location status for sample $sampleId failed.")
            }

        }
    }

}

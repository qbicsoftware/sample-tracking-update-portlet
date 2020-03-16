package life.qbic.portal.sampletracking.io

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.RxHttpClient
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Status
import life.qbic.datamodel.services.ServiceUser
import life.qbic.services.Service

// Noninstantiable utility class
class SampleTracker {

    // Suppress default constructor for noninstantiability
    private SampleTracker(){
        throw new AssertionError()
    }

    static SampleTrackingUpdate createSampleTrackingUpdate(Service service,
                                                           ServiceUser serviceUser) {
        new SampleTrackingCenter(service, serviceUser)
    }

    static SampleTrackingInformation createSampleTrackingInformation(Service service,
                                                                     ServiceUser serviceUser) {
        new SampleTrackingCenter(service, serviceUser)
    }

    static class SampleTrackingCenter implements SampleTrackingInformation, SampleTrackingUpdate {

        private final ServiceUser user

        private final Service service

        SampleTrackingCenter(Service service, ServiceUser user) {
            this.user = user
            this.service = service
        }

        @Override
        Location currentLocationForSample(String sampleId) throws SampleTrackingQueryException{
            HttpClient client = RxHttpClient.create(service.rootUrl)
            URI locationUri = new URI("${service.rootUrl.toExternalForm()}/samples/$sampleId/currentLocation/")

            HttpRequest request = HttpRequest.GET(locationUri).basicAuth(user.name, user.password)
            HttpResponse<Location> response

            client.withCloseable {
                response = it.toBlocking().exchange(request)
            }

            if (response?.status?.code != 200) {
                throw new SampleTrackingQueryException("Request for current location failed.")
            }
            if (!response?.body() instanceof Location) {
                throw new SampleTrackingQueryException("Did not receive a valid Location response.")
            }

            return response?.body()
        }

        @Override
        List<Location> availableLocationsForPersonWithEmail(String emailAdress) {
            HttpClient client = RxHttpClient.create(service.rootUrl)
            URI locationsUri = new URI("${service.rootUrl.toExternalForm()}/locations/$emailAdress")

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
        def updateLocationForSample(Location updatedLocation, String sampleId) throws SampleTrackingUpdateException {
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
        def updateStatusForSample(Status updatedStatus, String sampleId) throws SampleTrackingUpdateException {
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

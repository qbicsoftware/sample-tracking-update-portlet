package life.qbic.portal.sampletracking

import groovy.util.logging.Log4j2
import io.micronaut.http.client.exceptions.HttpClientException
import life.qbic.datamodel.services.ServiceUser
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.app.samples.list.ModifySampleList
import life.qbic.portal.sampletracking.app.samples.query.QuerySampleTrackingInfo
import life.qbic.portal.sampletracking.app.samples.query.SampleTrackingInformation
import life.qbic.portal.sampletracking.app.samples.update.SampleTrackingUpdate
import life.qbic.portal.sampletracking.app.samples.update.UpdateSampleTrackingInfo
import life.qbic.portal.sampletracking.io.SampleTracker
import life.qbic.portal.sampletracking.ui.PortletView
import life.qbic.portal.sampletracking.ui.SampleFileReceiver
import life.qbic.portal.sampletracking.app.SampleTrackingPortletController
import life.qbic.portal.utils.ConfigurationManager
import life.qbic.portal.utils.ConfigurationManagerFactory
import life.qbic.services.ConsulServiceFactory
import life.qbic.services.Service
import life.qbic.services.ServiceConnector
import life.qbic.services.ServiceType
import life.qbic.services.connectors.ConsulConnector

@Log4j2
class DependencyManager {

    private PortletView portletView

    private final List<Service> trackingServices

    private ServiceUser serviceUser

    private ConfigurationManager configManager

    DependencyManager() {
        initializeDependencies()
    }

    private void initializeDependencies() {

        configManager = ConfigurationManagerFactory.getInstance()
        serviceUser = configManager.getServiceUser()

        def queryInfoInteractor
        def updateInfoInteractor
        def sampleListInteractor = new ModifySampleList()
        try {
            // Set up tracking service components first
            setupSampleTrackingService()

            SampleTrackingInformation trackingInfoCenter = SampleTracker.createSampleTrackingInformation(trackingServices.get(0), serviceUser)
            SampleTrackingUpdate trackingUpdateCenter = SampleTracker.createSampleTrackingUpdate(trackingServices.get(0), serviceUser)

            //TODO add output interface implementation. -> the view
            queryInfoInteractor = new QuerySampleTrackingInfo(trackingInfoCenter)
            updateInfoInteractor = new UpdateSampleTrackingInfo(trackingUpdateCenter)
        } catch (HttpClientException e){
            log.error("Could not connect to sample tracking service.", e)
        }
        // Todo
        PortletController controller = new SampleTrackingPortletController(queryInfoInteractor, updateInfoInteractor, sampleListInteractor)
        SampleFileReceiver sampleFileReceiver = new SampleFileReceiver()
        PortletView view = new PortletView(controller, sampleFileReceiver)

        // set output to use cases
        queryInfoInteractor?.injectSampleUpdateOutput(view)
        updateInfoInteractor?.injectSampleStatusOutput(view)
        sampleListInteractor?.injectSampleListOutput(view)



        //make view available for UI class
        this.portletView = view

    }

    private void setupSampleTrackingService() {
        try {
            URL serviceURL = new URL(configManager.getServicesRegistryUrl())
            ServiceConnector connector = new ConsulConnector(serviceURL)
            ConsulServiceFactory factory = new ConsulServiceFactory(connector)
            trackingServices.addAll(factory.getServicesOfType(ServiceType.SAMPLE_TRACKING))

            if (trackingServices.isEmpty()) {
                log.error("No sample tracking service instance found.")
            }
        } catch (Exception e) {
            log.error("Unexpected error during setup of sample tracking service. {}", e.getMessage(), e)
            throw e
        }
    }

    protected PortletView getPortletView() {
        return this.portletView
    }
}

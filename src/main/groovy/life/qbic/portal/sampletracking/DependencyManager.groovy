package life.qbic.portal.sampletracking

import groovy.util.logging.Log4j2
import life.qbic.datamodel.services.ServiceUser
import life.qbic.portal.sampletracking.app.PortletController
import life.qbic.portal.sampletracking.app.samples.query.QuerySampleTrackingInfo
import life.qbic.portal.sampletracking.app.samples.query.SampleTrackingInformation
import life.qbic.portal.sampletracking.app.samples.update.SampleTrackingUpdate
import life.qbic.portal.sampletracking.app.samples.update.UpdateSampleTrackingInfo
import life.qbic.portal.sampletracking.io.SampleTracker
import life.qbic.portal.sampletracking.ui.PortletView
import life.qbic.portal.sampletracking.ui.SampleFileReceiver
import life.qbic.portal.sampletracking.app.SampleTrackingPortletController
import life.qbic.portal.utils.ConfigurationManager
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

        // Set up tracking service components first
        setupSampleTrackingService()

        SampleTrackingInformation trackingInfoCenter = SampleTracker.createSampleTrackingInformation(trackingServices.get(0), serviceUser)
        SampleTrackingUpdate trackingUpdateCenter = SampleTracker.createSampleTrackingUpdate(trackingServices.get(0), serviceUser)

        def queryInfoInteractor = new QuerySampleTrackingInfo(trackingInfoCenter)
        def updateInfoInteractor = new UpdateSampleTrackingInfo(trackingUpdateCenter)

        // Todo
        PortletController controller = new SampleTrackingPortletController()
        SampleFileReceiver sampleFileReceiver = new SampleFileReceiver()
        PortletView view = new PortletView(controller, sampleFileReceiver)


        //make view available for UI class
        this.portletView = view






    }

    private void setupSampleTrackingService() {
        URL serviceURL = new URL(configManager.getServicesRegistryUrl())
        ServiceConnector connector = new ConsulConnector(serviceURL)
        ConsulServiceFactory factory = new ConsulServiceFactory(connector)
        trackingServices.addAll(factory.getServicesOfType(ServiceType.SAMPLE_TRACKING))

        if (trackingServices.isEmpty()) {
            log.error("No sample tracking service instance found.")
        }
    }

    protected PortletView getPortletView() {
        return this.portletView
    }
}

package life.qbic.portal.sampletracking

import com.vaadin.ui.Upload
import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.datamodel.services.ServiceUser
import life.qbic.portal.sampletracking.web.controllers.SampleTrackingPortletController
import life.qbic.portal.sampletracking.trackinginformation.query.QuerySampleTrackingInfo
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryDataSource
import life.qbic.portal.sampletracking.trackinginformation.query.SampleListOutput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateDataSource
import life.qbic.portal.sampletracking.trackinginformation.update.UpdateSampleTrackingInfo
import life.qbic.portal.sampletracking.datasources.SampleTracker
import life.qbic.portal.sampletracking.web.*
import life.qbic.portal.sampletracking.web.presenters.SampleListPresenter
import life.qbic.portal.sampletracking.web.views.ControlElements
import life.qbic.portal.sampletracking.web.views.PortletView
import life.qbic.portal.sampletracking.web.views.SampleImport
import life.qbic.portal.sampletracking.web.views.SampleList
import life.qbic.portal.utils.ConfigurationManager
import life.qbic.portal.utils.ConfigurationManagerFactory
import life.qbic.services.ConsulServiceFactory
import life.qbic.services.Service
import life.qbic.services.ServiceConnector
import life.qbic.services.ServiceType
import life.qbic.services.connectors.ConsulConnector

@Log4j2
class DependencyManager {
    private SampleTrackingPortletController portletController
    private PortletView portletView

    private final List<Service> trackingServices

    private ServiceUser serviceUser

    private ConfigurationManager configManager
    private QuerySampleTrackingInfo queryInfoInteractor
    private UpdateSampleTrackingInfo updateInfoInteractor
    private ViewModel viewModel

    DependencyManager() {
        initializeDependencies()
    }

    private void initializeDependencies() {

        // read session information
        configManager = ConfigurationManagerFactory.getInstance()
        serviceUser = configManager.getServiceUser()

        // search for tracking services
        try {
            setupSampleTrackingService()
        } catch (Exception e) {
            log.error("Could not setup sample tracking service.", e)
        }

        // setup view models
        try {
            this.viewModel = new ViewModel(new ArrayList<Sample>(), new ArrayList<Location>(), new ArrayList<String>())
        } catch (Exception e) {
            log.error("Unexpected excpetion during ${ViewModel.getSimpleName()} view model setup.", e)
            throw e
        }

        // setup use cases
        setupUseCaseInteractors()

        // setup controllers
        try {
            this.portletController = new SampleTrackingPortletController(this.updateInfoInteractor, this.queryInfoInteractor)
        } catch (Exception e) {
            log.error("Unexpected exception during ${SampleTrackingPortletController.getSimpleName()} setup.", e)
        }
        // setup views
        setupViews()

    }

    /**
     * Searches for sample tracking services and stores them in a list.
     */
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
            log.error("Unexpected error during setup of sample tracking service.", e)
            throw e
        }
    }

    /**
     * Creates and sets use case interactors for all required use cases.
     * All Exceptions are handled and logged. Skips use cases where instantiation fails.
     */
    private void setupUseCaseInteractors() {
        try {
            SampleTrackingQueryDataSource trackingInfoCenter = SampleTracker.createSampleTrackingInformation(trackingServices.get(0), this.serviceUser)
            this.queryInfoInteractor = new QuerySampleTrackingInfo(trackingInfoCenter, this.viewModel as SampleListOutput)
        } catch (Exception e) {
            log.error("Could not setup ${QuerySampleTrackingInfo.getSimpleName()} use case", e)
        }

        try {
            final def presenter = new SampleListPresenter(viewModel)
            SampleTrackingUpdateDataSource trackingUpdateCenter = SampleTracker.createSampleTrackingUpdate(trackingServices.get(0), this.serviceUser)
            this.updateInfoInteractor = new UpdateSampleTrackingInfo(trackingUpdateCenter, presenter)
        } catch (Exception e) {
            log.error("Could not setup ${UpdateSampleTrackingInfo.getSimpleName()} use case", e)
        }

    }

    /**
     * Creates and sets the main view and it's sub views.
     * Provides each view with a corresponding controller and model
     * Exceptions are thrown since view generation should not fail.
     */
    private void setupViews() {

        SampleImport sampleImport
        try {
            SampleFileReceiver sampleFileReceiver = new SampleFileReceiver()
            sampleImport = new SampleImport(this.portletController, sampleFileReceiver as Upload.Receiver)
        } catch (Exception e) {
            log.error("Could not create ${SampleImport.getSimpleName()} view.", e)
            throw e
        }

        SampleList sampleList
        try {
            sampleList = new SampleList(this.viewModel)
        } catch (Exception e) {
            log.error("Could not create ${SampleList.getSimpleName()} view.", e)
            throw e
        }

        ControlElements sampleModifyControls
        try {
            sampleModifyControls = new ControlElements(this.portletController, this.viewModel)
        } catch (Exception e) {
            log.error("Could not create ${ControlElements.getSimpleName()} view.", e)
            throw e
        }

        PortletView portletView
        try {
            portletView = new PortletView(this.portletController, this.viewModel, sampleList, sampleModifyControls, sampleImport)
            this.portletView = portletView
        } catch (Exception e) {
            log.error("Could not create ${PortletView.getSimpleName()} view.", e)
            throw e
        }
    }

    /**
     *
     * @return the main view of the portlet
     */
    protected PortletView getPortletView() {
        return this.portletView
    }
}

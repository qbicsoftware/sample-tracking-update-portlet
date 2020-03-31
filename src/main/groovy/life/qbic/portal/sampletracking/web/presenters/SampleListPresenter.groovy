package life.qbic.portal.sampletracking.web.presenters

import groovy.util.logging.Log4j2
import life.qbic.datamodel.identifiers.TooManySamplesException
import life.qbic.datamodel.samples.Location
import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.query.SampleTrackingQueryOutput
import life.qbic.portal.sampletracking.trackinginformation.update.SampleTrackingUpdateOutput
import life.qbic.portal.sampletracking.web.ViewModel

import java.rmi.UnexpectedException

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 */
@Log4j2
class SampleListPresenter implements SampleTrackingUpdateOutput, SampleTrackingQueryOutput{

    private final ViewModel viewModel

    SampleListPresenter(ViewModel viewModel) {
        this.viewModel = viewModel
    }


    @Override
    def invokeOnError(String msg) {
        viewModel.notifications.add(msg)
    }

    @Override
    void updateFinished() {
        viewModel.samples.clear()
    }

    @Override
    def updateAvailableLocations(List<Location> locations) {
        // This has no effect here.
        throw new AssertionError("Access to this method not intended.")
    }

    @Override
    def updateCurrentLocation(String sampleId, Location location) {
        List<Sample> matchingSamples = this.viewModel.samples.findAll({
            sample -> ((sample as Sample).getCode() == sampleId)
        })
        if (matchingSamples.size() > 1) {
            Exception e = new TooManySamplesException()
            log.error("Found ${matchingSamples.size()} identical sampleIds.", e)
            invokeOnError("There are multiple samples with the same ID.")
        } else if (matchingSamples.size() < 1) {
            log.warn("Tried to update location of sample not included in the selection.")
            invokeOnError("The sample ${sampleId} is not selected.")
        } else {
            matchingSamples.forEach({Sample sample -> sample.setCurrentLocation(location)})
        }
    }

    @Override
    def addSampleToList(Sample sample) {
        this.viewModel.samples.add(sample)
    }
}

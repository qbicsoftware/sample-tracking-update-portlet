package life.qbic.portal.sampletracking.web.presenters

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.trackinginformation.list.ModifySampleListOutput
import life.qbic.portal.sampletracking.web.ViewModel

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 *
 */
class SampleImportPresenter implements ModifySampleListOutput{

    final ViewModel viewModel

    SampleImportPresenter(ViewModel viewModel) {
        this.viewModel = Objects.requireNonNull(viewModel, "ViewModel must not be null")
    }

    @Override
    void addSamples(List<Sample> samples) {
        viewModel.samples << samples
    }

    @Override
    void removeSamples(List<Sample> samples) {
        viewModel.samples.removeAll(samples)
    }

    @Override
    void clearSamples() {
        viewModel.samples.clear()
    }

    @Override
    def invokeOnError(String msg) {
        return null
    }
}

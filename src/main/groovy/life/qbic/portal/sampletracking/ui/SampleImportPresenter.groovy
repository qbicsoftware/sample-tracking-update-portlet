package life.qbic.portal.sampletracking.ui

import life.qbic.datamodel.samples.Sample
import life.qbic.portal.sampletracking.app.samples.list.ModifySampleListOutput

/**
 * <add class description here>
 *
 * @author: Sven Fillinger
 *
 */
class SampleImportPresenter implements ModifySampleListOutput, SampleImportModel{


    @Override
    void addSamples(Sample... samples) {

    }

    @Override
    void removeSamples(Sample... samples) {

    }

    @Override
    void clearSamples() {

    }

    @Override
    def invokeOnError(String msg) {
        return null
    }
}

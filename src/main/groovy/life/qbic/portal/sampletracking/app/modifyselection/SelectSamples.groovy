package life.qbic.portal.sampletracking.app.modifyselection

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample

@Log4j2
class SelectSamples {

    void selectSample(Sample sample) {

    }

    void selectMultipleSamples(List<Sample> samples) {
        for (sample in samples) {
            selectSample(sample);
        }
    }

}

package life.qbic.portal.sampletracking.app.modifyselection

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample

@Log4j2
class RemoveSelection {

    void removeAll(List<Sample> samples) {
        for (sample in samples) {
            removeSample(sample)
        }
    }

    void removeSample(Sample sample) {}
}

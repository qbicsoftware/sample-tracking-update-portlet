package life.qbic.portal.sampletracking.app.modifyselection

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample

@Log4j2
class RemoveSelection {

    static List<Sample> removeAll(List<Sample> selection) {
        for (sample in selection) {
            removeSample(sample)
        }
    }

    static List<Sample> removeSample(Sample sample, List<Sample> selection) {}
}

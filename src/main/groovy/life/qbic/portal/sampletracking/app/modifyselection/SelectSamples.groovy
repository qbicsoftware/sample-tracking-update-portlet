package life.qbic.portal.sampletracking.app.modifyselection

import groovy.util.logging.Log4j2
import life.qbic.datamodel.samples.Sample

@Log4j2
class SelectSamples {

    static List<Sample> selectSample(Sample sample, List<Sample> currentSelection) {

    }

    static List<Sample> selectMultipleSamples(List<Sample> samples, List<Sample> currentSelection) {
        for (sample in samples) {
            selectSample(sample, currentSelection);
        }
    }

    static List<Sample> selectFromCSV(File csvSelection) {

    }

}

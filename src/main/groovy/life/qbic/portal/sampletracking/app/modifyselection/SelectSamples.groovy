package life.qbic.portal.sampletracking.app.modifyselection

import life.qbic.datamodel.samples.Sample

class SelectSamples {

    void selectSample(Sample sample) {

    }

    void selectMultipleSamples(List<Sample> samples) {
        for (sample in samples) {
            selectSample(sample);
        }
    }

}

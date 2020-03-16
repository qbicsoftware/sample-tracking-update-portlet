package life.qbic.portal.sampletracking.app.modifyselection

import life.qbic.datamodel.samples.Sample

interface SelectSamplesInput {
    List<Sample> selectSample(Sample sample, List<Sample> currentSelection)
    List<Sample> selectMultipleSamples(List<Sample> samples, List<Sample> currentSelection)
    List<Sample> selectFromCSV(File csvSelection)
}
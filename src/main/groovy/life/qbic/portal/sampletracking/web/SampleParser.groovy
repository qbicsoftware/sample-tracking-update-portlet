package life.qbic.portal.sampletracking.web

import life.qbic.datamodel.identifiers.SampleCodeFunctions

/**
 * <h1>Sample Parser</h1>
 *
 * <p>Extracts sample codes from a given input</p>
 *
 * @since 1.3.0
 */
class SampleParser {
    private static String COLUMN_SEPARATOR = "\t"

    /**
     * Extracts QBiC barcodes from a (multi-)line string in TSV format.
     * The barcode has to be contained in the first column. Header rows are ignored
     * @param string the QBiC barcodes in TSV format
     * @return a collection of extracted sample codes
     */
    static Collection<String> extractSampleCodes(String string) {
        Collection<String> sampleCodes = new ArrayList<>()
        List<String> lines = string.split("\n").toList()
        for (line in lines) {
            Optional<String> sampleCode = parseLine(line)
            sampleCode.ifPresent({
                sampleCodes.add(sampleCode.get())
            })
        }
        return sampleCodes
    }

    private static Optional<String> parseLine(String line) {
        Optional<String> sampleCode = Optional.empty()
        String[] columns = line.split(COLUMN_SEPARATOR).toList()
        String suspectedBarcode = columns[0].trim()
        if (SampleCodeFunctions.isQbicBarcode(suspectedBarcode)) {
            sampleCode = Optional.of(suspectedBarcode)
        }
        return sampleCode
    }
}

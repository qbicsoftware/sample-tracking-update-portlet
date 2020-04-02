package life.qbic.portal.sampletracking.web.views.samplefile


class UploadSucceededEvent {
    List<String> sampleIds
    String sourceFile

    UploadSucceededEvent(List<String> sampleIds, String sourceFile) {
        this.sampleIds = sampleIds
        this.sourceFile = sourceFile
    }

}

package life.qbic.portal.sampletracking.web.views.samplefile


class UploadSucceededEvent {
    List<String> sampleIds

    UploadSucceededEvent(Set<String> sampleIds) {
        this.sampleIds = sampleIds.asList()
    }

}

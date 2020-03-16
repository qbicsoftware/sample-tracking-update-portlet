package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.VerticalLayout
import life.qbic.datamodel.samples.Sample

class SampleList extends VerticalLayout{
    private List<Sample> samples

    SampleList() {
        super()
        initLayout()
    }

    private def initLayout() {

    }

    void refreshList() {}
}

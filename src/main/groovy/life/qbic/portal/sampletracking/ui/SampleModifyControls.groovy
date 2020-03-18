package life.qbic.portal.sampletracking.ui

import com.vaadin.ui.Button
import com.vaadin.ui.VerticalLayout
import life.qbic.portal.sampletracking.app.PortletController

class SampleModifyControls extends VerticalLayout{

    private PortletController controller
    private Button clearButton

    SampleModifyControls(PortletController portletController) {
        super()
        this.controller = portletController
        initLayout()
        registerListeners()
    }

    private def initLayout() {
        clearButton = new Button("Clear Selection")
        this.addComponent(clearButton)
    }

    private void registerListeners() {
        this.clearButton.addClickListener({ event -> controller.clearSelection() })
    }
}

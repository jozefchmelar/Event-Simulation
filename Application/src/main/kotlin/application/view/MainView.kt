package application.view

import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("Hello TornadoFX") {


    override val root = borderpane {
        minWidth = 600.0

        center = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab(FixedMinibusView::class)
            tab(FixedEmployeeView::class)
            tab(ReplicationView::class)
            tab(SimulationView::class)
        }
    }

}

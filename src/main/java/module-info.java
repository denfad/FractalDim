module ru.denfad.fractaldim {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;

    opens ru.denfad.fractaldim to javafx.fxml, java.base;
    exports ru.denfad.fractaldim;
    exports ru.denfad.fractaldim.Controller;
    exports ru.denfad.fractaldim.Model;
    opens ru.denfad.fractaldim.Controller to javafx.fxml;
    exports ru.denfad.fractaldim.Service;
    exports ru.denfad.fractaldim.Service.Generators;

}
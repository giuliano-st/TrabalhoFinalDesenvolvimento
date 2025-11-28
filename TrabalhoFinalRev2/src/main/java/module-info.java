module inf.grupo.trabalhofinalrev2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;


    opens inf.grupo.trabalhofinalrev2 to javafx.fxml;
    opens inf.grupo.trabalhofinalrev2.controller to javafx.fxml;
    exports inf.grupo.trabalhofinalrev2;
}
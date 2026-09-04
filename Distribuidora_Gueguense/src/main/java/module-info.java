module ni.edu.uam.distribuidora_gueguense {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens ni.edu.uam.distribuidora_gueguense to javafx.fxml;
    opens ni.edu.uam.distribuidora_gueguense.controller to javafx.fxml;
    opens ni.edu.uam.distribuidora_gueguense.models to javafx.base;

    exports ni.edu.uam.distribuidora_gueguense;
    exports ni.edu.uam.distribuidora_gueguense.controller;
    exports ni.edu.uam.distribuidora_gueguense.models;
}
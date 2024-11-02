module com.gladosmnnit.calcij {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gladosmnnit.calcij to javafx.fxml;
    exports com.gladosmnnit.calcij;
}
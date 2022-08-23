module com.groupid {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens com.groupid to javafx.fxml;
    exports com.groupid;
}

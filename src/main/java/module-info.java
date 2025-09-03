module lk.ijse.project.drivemaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;
    requires javafx.base;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.management;
    requires jakarta.validation;
    requires bcrypt;
    requires java.sql;

    opens lk.ijse.project.drivemaster.config to jakarta.persistence;
    opens lk.ijse.project.drivemaster.entity to org.hibernate.orm.core;

    opens lk.ijse.project.drivemaster.controller to javafx.fxml;
    exports lk.ijse.project.drivemaster;
    opens lk.ijse.project.drivemaster.enums to org.hibernate.orm.core;
}
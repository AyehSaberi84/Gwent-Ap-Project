module ap.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires org.apache.tomcat.embed.core;
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.servlet;
    requires java.mail;
    requires aerogear.otp.java;
    requires com.google.gson;
    opens model to com.google.gson;
    opens model.factions to com.google.gson;
    exports controller;
    exports model;
    exports model.factions;
    exports model.commanders;
    opens controller to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
    opens model.Enum to com.google.gson;
}
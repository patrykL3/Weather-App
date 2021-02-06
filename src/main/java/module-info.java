module WeatherApp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    requires fastjson;
    requires openweathermap.api;
    requires java.sql;
    requires com.ibm.icu;
    requires java.desktop;

    opens pl.patryklubik;
    opens pl.patryklubik.view;
    opens pl.patryklubik.controller;
    opens pl.patryklubik.controller.services;
    opens pl.patryklubik.model;
}
package me.niculicicris.filestore.application.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import me.niculicicris.filestore.application.navigation.abstraction.INavigator;
import me.niculicicris.filestore.common.annotation.Inject;

public class ApplicationController {
    private final INavigator navigator;

    @FXML
    private Pane root;

    @Inject
    public ApplicationController(INavigator navigator) {
        this.navigator = navigator;
    }

    @FXML
    public void initialize() {
        navigator.initialize(root);
        navigator.navigate("login");
    }
}

package me.niculicicris.filestore.application.navigation.abstraction;

import javafx.scene.layout.Pane;

public interface INavigator {
    void initialize(Pane root);

    void navigate(String route);
}

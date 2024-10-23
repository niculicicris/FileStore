package me.niculicicris.filestore.application;

import atlantafx.base.theme.PrimerLight;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.niculicicris.filestore.Main;
import me.niculicicris.filestore.application.abstraction.IApplication;
import me.niculicicris.filestore.application.loader.IFxmlLoader;
import me.niculicicris.filestore.common.annotation.Inject;

public class Application implements IApplication {
    private final IFxmlLoader fxmlLoader;
    private final Stage stage;

    @Inject
    public Application(IFxmlLoader fxmlLoader, Stage stage) {
        this.fxmlLoader = fxmlLoader;
        this.stage = stage;
    }

    @Override
    public void run() {
        var scene = createScene();

        setTheme();
        displayScene(scene);
    }

    private Scene createScene() {
        var applicationView = fxmlLoader.load("/application/application-view.fxml");
        return new Scene(applicationView, 1200, 800);
    }

    private void setTheme() {
        Main.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    }

    private void displayScene(Scene scene) {
        stage.setTitle("FileStore");
        stage.setScene(scene);
        stage.show();
    }
}

package me.niculicicris.filestore.application;

import atlantafx.base.theme.PrimerDark;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.niculicicris.filestore.Main;
import me.niculicicris.filestore.application.abstraction.IApplication;
import me.niculicicris.filestore.application.infrastructure.loader.IViewLoader;
import me.niculicicris.filestore.common.annotation.Inject;

public class Application implements IApplication {
    private final IViewLoader viewLoader;
    private final Stage stage;

    @Inject
    public Application(IViewLoader viewLoader, Stage stage) {
        this.viewLoader = viewLoader;
        this.stage = stage;
    }

    @Override
    public void run() {
        var scene = createScene();

        setTheme();
        displayScene(scene);
    }

    private Scene createScene() {
        var applicationView = viewLoader.load("/application/application-view.fxml");
        return new Scene(applicationView, 1200, 800);
    }

    private void setTheme() {
        Main.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    }

    private void displayScene(Scene scene) {
        stage.setTitle("FileStore");
        stage.setScene(scene);
        stage.show();
    }
}

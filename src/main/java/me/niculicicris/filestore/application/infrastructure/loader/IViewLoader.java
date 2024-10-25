package me.niculicicris.filestore.application.infrastructure.loader;

import javafx.scene.Parent;

public interface IViewLoader {
    Parent load(String resource);

    Parent load(String resource, Object controller);

    void unload(String resource);
}

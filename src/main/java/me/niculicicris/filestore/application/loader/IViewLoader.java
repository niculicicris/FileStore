package me.niculicicris.filestore.application.loader;

import javafx.scene.Parent;

public interface IViewLoader {
    Parent load(String resource);

    void unload(String resource);
}

package me.niculicicris.filestore.application.controller.file;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import me.niculicicris.filestore.application.infrastructure.error.IErrorHandler;
import me.niculicicris.filestore.application.infrastructure.loader.IViewLoader;
import me.niculicicris.filestore.application.io.reader.IFileReader;
import me.niculicicris.filestore.application.io.writer.IFileWriter;
import me.niculicicris.filestore.application.navigation.abstraction.INavigator;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.model.FileDescriptor;
import me.niculicicris.filestore.service.file.abstraction.IFileService;

import java.util.ArrayList;
import java.util.List;

public class FileExplorerController {
    private final IViewLoader viewLoader;
    private final INavigator navigator;
    private final IFileService fileService;
    private final IFileReader fileReader;
    private final IFileWriter fileWriter;
    private final IErrorHandler errorHandler;
    private final List<FileDescriptor> fileDescriptors = new ArrayList<>();

    @FXML
    protected Pane filesRoot;

    @Inject
    public FileExplorerController(IViewLoader viewLoader,
                                  INavigator navigator,
                                  IFileService fileService,
                                  IFileReader fileReader,
                                  IFileWriter fileWriter,
                                  IErrorHandler errorHandler) {
        this.viewLoader = viewLoader;
        this.navigator = navigator;
        this.fileService = fileService;
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
        this.errorHandler = errorHandler;
    }

    @FXML
    protected void initialize() {
        updateFiles();
    }

    public void storeFile() {
        var optionalFile = fileReader.read();

        if (optionalFile.isEmpty()) return;
        var result = fileService.storeFile(optionalFile.get());

        result.match(this::updateFiles, errorHandler::handleError);
    }

    private void retrieveFile(FileDescriptor descriptor) {
        var result = fileService.getFile(descriptor.name());
        result.match(fileWriter::write, errorHandler::handleError);
    }

    private void deleteFile(FileDescriptor descriptor) {
        var result = fileService.deleteFile(descriptor.name());
        result.match(this::updateFiles, errorHandler::handleError);
    }

    private void updateFiles() {
        loadFileDescriptors();
        updateFilesRoot();
    }

    private void loadFileDescriptors() {
        var result = fileService.getFileDescriptors();

        result.match(
                descriptors -> {
                    fileDescriptors.clear();
                    fileDescriptors.addAll(descriptors);
                },
                error -> {
                    navigator.navigate("login");
                    errorHandler.handleError(error);
                }
        );
    }

    private void updateFilesRoot() {
        var fileViews = fileDescriptors.stream().map(this::loadFileView).toList();

        if (fileViews.isEmpty()) {
            var label = createEmptyLabel();

            filesRoot.getChildren().clear();
            filesRoot.getChildren().add(label);

            return;
        }

        filesRoot.getChildren().clear();
        filesRoot.getChildren().addAll(fileViews);
    }

    private Label createEmptyLabel() {
        var label = new Label("You have no stored files");

        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("title-4");

        return label;
    }

    private Parent loadFileView(FileDescriptor descriptor) {
        var resource = "/application/file/file-view.fxml";
        var controller = new FileController(descriptor, () -> retrieveFile(descriptor), () -> deleteFile(descriptor));

        return viewLoader.load(resource, controller);
    }
}

package me.niculicicris.filestore.application.io.writer;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.niculicicris.filestore.application.infrastructure.error.IErrorHandler;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.model.StoredFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter implements IFileWriter {
    private final Stage stage;
    private final IErrorHandler errorHandler;

    @Inject
    public FileWriter(Stage stage, IErrorHandler errorHandler) {
        this.stage = stage;
        this.errorHandler = errorHandler;
    }

    @Override
    public void write(StoredFile file) {
        var fileChooser = createFileChooser(file.name());
        var savingFile = fileChooser.showSaveDialog(stage);

        if (savingFile != null) {
            saveFile(savingFile, file.content());
        }
    }

    private FileChooser createFileChooser(String name) {
        var fileChooser = new FileChooser();

        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName(name);

        return fileChooser;
    }

    private void saveFile(File file, byte[] content) {
        try (var outputStream = new FileOutputStream(file)) {
            outputStream.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

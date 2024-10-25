package me.niculicicris.filestore.application.io.reader;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.dto.FileDto;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

public class FileReader implements IFileReader {
    private final Stage stage;

    @Inject
    public FileReader(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Optional<FileDto> read() {
        var fileChooser = createFileChooser();
        var readingFile = fileChooser.showOpenDialog(stage);
        var content = openFile(readingFile);

        if (content.isEmpty()) {
            return Optional.empty();
        }

        var file = new FileDto(readingFile.getName(), content.get());
        return Optional.of(file);
    }

    private FileChooser createFileChooser() {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");

        return fileChooser;
    }

    private Optional<byte[]> openFile(File file) {
        try (var inputStream = new FileInputStream(file)) {
            return Optional.of(inputStream.readAllBytes());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
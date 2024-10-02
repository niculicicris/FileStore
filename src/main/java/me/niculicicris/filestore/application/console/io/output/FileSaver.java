package me.niculicicris.filestore.application.console.io.output;

import me.niculicicris.filestore.application.console.abstraction.IErrorHandler;
import me.niculicicris.filestore.application.console.io.output.abstraction.IFileSaver;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.dto.FileDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaver implements IFileSaver {
    private final IErrorHandler errorHandler;

    @Inject
    public FileSaver(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void saveFile(FileDto file) {
        File outputFile = new File("./" + file.name());

        if (outputFile.exists()) {
            errorHandler.handleError("File.System.Duplicate", file.name());
            return;
        }

        createFile(outputFile);
        writeFile(outputFile, file.content());
        System.out.println("File retrieved successfully!");
    }

    private void createFile(File file) {
        try {
            var isCreated = file.createNewFile();

            if (!isCreated) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void writeFile(File file, byte[] content) {
        try (var outputStream = new FileOutputStream(file)) {
            outputStream.write(content);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

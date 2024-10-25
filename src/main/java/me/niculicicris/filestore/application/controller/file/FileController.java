package me.niculicicris.filestore.application.controller.file;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import me.niculicicris.filestore.data.model.FileDescriptor;

public class FileController {
    private final ObjectProperty<FileDescriptor> fileDescriptor = new SimpleObjectProperty<>();
    private final Runnable retrieveCallback;
    private final Runnable deleteCallback;

    public FileController(FileDescriptor fileDescriptor,
                          Runnable retrieveCallback,
                          Runnable deleteCallback) {
        this.fileDescriptor.set(fileDescriptor);
        this.retrieveCallback = retrieveCallback;
        this.deleteCallback = deleteCallback;
    }

    public String getFileName() {
        return fileDescriptor.get().name();
    }

    public String getFileSize() {
        var size = fileDescriptor.get().size();

        if (size < 1024) {
            return size + " Bytes";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }

    public ObjectProperty<FileDescriptor> fileDescriptorProperty() {
        return fileDescriptor;
    }

    public void onRetrieveItemClick() {
        retrieveCallback.run();
    }

    public void onDeleteItemClick() {
        deleteCallback.run();
    }
}

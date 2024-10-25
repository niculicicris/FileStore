package me.niculicicris.filestore.application.io.writer;

import me.niculicicris.filestore.data.model.StoredFile;

public interface IFileWriter {
    void write(StoredFile file);
}

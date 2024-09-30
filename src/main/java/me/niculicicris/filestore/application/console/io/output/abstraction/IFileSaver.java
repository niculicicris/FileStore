package me.niculicicris.filestore.application.console.io.output.abstraction;

import me.niculicicris.filestore.data.dto.FileDto;

public interface IFileSaver {
    void saveFile(FileDto file);
}

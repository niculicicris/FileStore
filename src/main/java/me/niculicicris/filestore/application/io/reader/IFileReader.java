package me.niculicicris.filestore.application.io.reader;

import me.niculicicris.filestore.data.dto.FileDto;

import java.util.Optional;

public interface IFileReader {
    Optional<FileDto> read();
}

package me.niculicicris.filestore.application.console.io.input.abstraction;

import me.niculicicris.filestore.data.dto.FileDto;

import java.util.Optional;

public interface IFileReader {
    Optional<FileDto> readFile();
}

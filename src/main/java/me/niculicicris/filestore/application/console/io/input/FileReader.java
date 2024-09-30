package me.niculicicris.filestore.application.console.io.input;

import me.niculicicris.filestore.application.console.io.input.abstraction.IFileReader;
import me.niculicicris.filestore.application.console.io.input.abstraction.IStringReader;
import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.data.dto.FileDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class FileReader implements IFileReader {
    private final IStringReader stringReader;

    @Inject
    public FileReader(IStringReader stringReader) {
        this.stringReader = stringReader;
    }

    @Override
    public Optional<FileDto> readFile() {
        var fileName = stringReader.readString("Enter file name: ");
        var fileContent = getFile(fileName);

        if (fileContent == null) return Optional.empty();
        var file = new FileDto(fileName, fileContent);

        return Optional.of(file);
    }

    private byte[] getFile(String fileName) {
        var file = new File(fileName);

        try (var inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            return null;
        }
    }
}
package me.niculicicris.filestore.application.console.input;

import me.niculicicris.filestore.application.console.input.abstraction.IFileReader;
import me.niculicicris.filestore.application.console.input.abstraction.IStringReader;
import me.niculicicris.filestore.common.annotation.Inject;

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
    public Optional<byte[]> readFile() {
        var fileName = stringReader.readString("Enter file name: ");
        return Optional.ofNullable(getFile(fileName));
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
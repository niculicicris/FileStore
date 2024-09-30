package me.niculicicris.filestore.application.console.input.abstraction;

import java.util.Optional;

public interface IFileReader {
    Optional<byte[]> readFile();
}

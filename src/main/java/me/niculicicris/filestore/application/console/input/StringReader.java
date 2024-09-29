package me.niculicicris.filestore.application.console.input;

import me.niculicicris.filestore.application.console.input.abstraction.IStringReader;
import me.niculicicris.filestore.common.annotation.Inject;

import java.util.Scanner;

public class StringReader implements IStringReader {
    private final Scanner scanner;

    @Inject
    public StringReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
}

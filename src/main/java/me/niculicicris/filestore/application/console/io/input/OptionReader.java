package me.niculicicris.filestore.application.console.io.input;

import me.niculicicris.filestore.application.console.io.input.abstraction.IOptionReader;
import me.niculicicris.filestore.common.annotation.Inject;

import java.util.Scanner;

public class OptionReader implements IOptionReader {
    private final Scanner scanner;

    @Inject
    public OptionReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int readOption(int optionsNumber) {
        var option = readNumber(scanner);
        while (option < 1 || option > optionsNumber) {
            System.out.println("Please enter a number between 1 and " + (optionsNumber) + ".");
            option = readNumber(scanner);
        }

        return option;
    }

    private int readNumber(Scanner scanner) {
        System.out.print("Enter option: ");
        return scanner.nextInt();
    }
}

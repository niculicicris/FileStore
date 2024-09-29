package me.niculicicris.filestore.application.console.abstraction;

import me.niculicicris.filestore.application.console.input.abstraction.IOptionReader;

import java.util.ArrayList;
import java.util.List;

public abstract class ConsolePanel implements IConsolePanel {
    private final String title;
    protected final IErrorHandler errorHandler;
    private final IOptionReader optionReader;
    private final List<OptionHandler> handlers = new ArrayList<>();
    private boolean shouldStop;

    public ConsolePanel(String title,
                        IErrorHandler errorHandler,
                        IOptionReader optionReader) {
        this.title = title;
        this.errorHandler = errorHandler;
        this.optionReader = optionReader;
    }

    @Override
    public void run() {
        while (!shouldStop) {
            displayOptions();
            var option = readOption();
            processOption(option);
        }
    }

    protected void addHandler(OptionHandler handler) {
        handlers.add(handler);
    }

    protected void stop() {
        this.shouldStop = true;
    }

    private void displayOptions() {
        System.out.println(title + ":");

        for (int index = 0; index < handlers.size(); index++) {
            OptionHandler option = handlers.get(index);
            displayOption(index + 1, option.getTitle());
        }

        displayOption(optionsNumber(), "Exit");
    }

    private void displayOption(int index, String title) {
        System.out.println(index + ". " + title);
    }

    private int readOption() {
        return optionReader.readOption(optionsNumber());
    }

    private void processOption(int option) {
        if (option == optionsNumber()) exit();
        else runOption(option);

        System.out.println();
    }

    private void exit() {
        System.out.println("FileStore is closing...");
        Runtime.getRuntime().exit(0);
    }

    private void runOption(int option) {
        try {
            var optionIndex = option - 1;
            handlers.get(optionIndex).handle(this::stop);
        } catch (Exception ignore) {
            errorHandler.handleError("Server");
        }
    }

    private int optionsNumber() {
        return handlers.size() + 1;
    }
}

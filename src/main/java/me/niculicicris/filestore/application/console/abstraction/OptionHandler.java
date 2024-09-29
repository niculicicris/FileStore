package me.niculicicris.filestore.application.console.abstraction;

public abstract class OptionHandler {
    private final String title;

    public OptionHandler(String title) {
        this.title = title;
    }

    public abstract void handle(Runnable stop);

    public String getTitle() {
        return title;
    }
}

package me.niculicicris.filestore.application.console.abstraction;

public interface IOptionHandler {
    String getTitle();
    
    void handle(Runnable stop);
}

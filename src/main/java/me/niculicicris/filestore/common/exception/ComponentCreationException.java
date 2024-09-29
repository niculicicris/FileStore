package me.niculicicris.filestore.common.exception;

public class ComponentCreationException extends RuntimeException {

    public ComponentCreationException(Class<?> abstraction) {
        super("Failed to create an instance for the implementation of " + abstraction.getName() + ".");
    }
}

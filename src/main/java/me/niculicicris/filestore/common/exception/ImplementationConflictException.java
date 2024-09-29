package me.niculicicris.filestore.common.exception;

public class ImplementationConflictException extends RuntimeException {

    public ImplementationConflictException(Class<?> abstraction) {
        super("There is already an implementation for " + abstraction.getSimpleName() + ".");
    }
}

package me.niculicicris.filestore.common.exception;

public class UnresolvedComponentException extends RuntimeException {

    public UnresolvedComponentException(Class<?> abstraction) {
        super("Unable to resolve an implementation for " + abstraction.getName() + ".");
    }
}

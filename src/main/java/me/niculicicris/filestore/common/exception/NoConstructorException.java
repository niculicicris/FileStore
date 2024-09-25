package me.niculicicris.filestore.common.exception;

public class NoConstructorException extends RuntimeException {

    public NoConstructorException(Class<?> abstraction) {
        super("No default or injectable constructor has been found in the implementation of " + abstraction.getName() + ".");
    }
}

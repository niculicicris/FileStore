package me.niculicicris.filestore.common.exception;

public class NoInjectableConstructorException extends RuntimeException {

    public NoInjectableConstructorException(Class<?> implementation) {
        super("There is no injectable constructor for " + implementation.getName() + ".");
    }
}

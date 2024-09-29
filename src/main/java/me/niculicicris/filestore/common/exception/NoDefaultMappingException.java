package me.niculicicris.filestore.common.exception;

public class NoDefaultMappingException extends RuntimeException {

    public NoDefaultMappingException(Class<?> abstraction) {
        super("There is no default mapping for " + abstraction.getName() + ".");
    }
}

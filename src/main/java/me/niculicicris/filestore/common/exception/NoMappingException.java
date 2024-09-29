package me.niculicicris.filestore.common.exception;

public class NoMappingException extends RuntimeException {

    public NoMappingException(String key, Class<?> abstraction) {
        super("There is no mapping for " + abstraction.getName() + " with the key " + key + ".");
    }
}

package me.niculicicris.filestore.common.exception;

public class CircularDependencyException extends RuntimeException {

    public CircularDependencyException(Class<?> first, Class<?> second) {
        super("There is a circular dependency between implementations of " + first.getName() + " and " + second.getName() + ".");
    }
}

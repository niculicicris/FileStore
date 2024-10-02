package me.niculicicris.filestore.common.error;

public class FileError {

    private FileError() {
    }

    public static Error invalidFileName() {
        return new Error("File.Validation", ErrorType.VALIDATION, "");
    }

    public static Error alreadyStored(String name) {
        return new Error("File.Duplicate", ErrorType.CONFLICT, name);
    }

    public static Error notFound(String name) {
        return new Error("File.NotFound", ErrorType.NOT_FOUND, name);
    }
}

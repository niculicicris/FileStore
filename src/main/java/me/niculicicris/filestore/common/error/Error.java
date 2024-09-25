package me.niculicicris.filestore.common.error;

public class Error {
    private final String code;
    private final ErrorType type;
    private final String target;

    public Error(String code, ErrorType type, String target) {
        this.code = code;
        this.type = type;
        this.target = target;
    }

    public String getCode() {
        return code;
    }

    public ErrorType getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }
}

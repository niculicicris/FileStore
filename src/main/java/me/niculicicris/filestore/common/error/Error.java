package me.niculicicris.filestore.common.error;

public record Error(String code, ErrorType type, String target) {
}

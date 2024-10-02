package me.niculicicris.filestore.data.model;

public record StoredFile(String owner, String name, byte[] content) {
}

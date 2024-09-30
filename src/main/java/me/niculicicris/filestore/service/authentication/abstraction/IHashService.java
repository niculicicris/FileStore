package me.niculicicris.filestore.service.authentication.abstraction;

public interface IHashService {
    String hash(String value);

    boolean matches(String hash, String value);
}

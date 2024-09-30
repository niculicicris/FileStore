package me.niculicicris.filestore.service.authentication;

import me.niculicicris.filestore.service.authentication.abstraction.IHashService;

public class NoneHashService implements IHashService {

    @Override
    public String hash(String value) {
        return value;
    }

    @Override
    public boolean matches(String hash, String value) {
        return hash.equals(value);
    }
}

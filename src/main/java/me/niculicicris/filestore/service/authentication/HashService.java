package me.niculicicris.filestore.service.authentication;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.niculicicris.filestore.service.authentication.abstraction.IHashService;

public class HashService implements IHashService {

    @Override
    public String hash(String value) {
        return BCrypt.withDefaults().hashToString(12, value.toCharArray());
    }


    @Override
    public boolean matches(String hash, String value) {
        return BCrypt.verifyer().verify(value.toCharArray(), hash.toCharArray()).verified;
    }
}

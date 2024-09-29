package me.niculicicris.filestore.service;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.error.UserError;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.data.model.User;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;
import me.niculicicris.filestore.service.abstraction.IHashService;
import me.niculicicris.filestore.service.abstraction.IUserService;
import me.niculicicris.filestore.validation.abstraction.ICredentialsValidator;

public class UserService implements IUserService {
    private final IHashService hashService;
    private final ICredentialsValidator validator;
    private final IUserRepository userRepository;

    @Inject
    public UserService(IHashService hashService,
                       ICredentialsValidator validator,
                       IUserRepository userRepository) {
        this.hashService = hashService;
        this.validator = validator;
        this.userRepository = userRepository;
    }

    @Override
    public IEmptyResult registerUser(UserCredentialsDto credentials) {
        var username = credentials.username();
        var password = credentials.password();

        var validationResult = validator.validate(credentials);
        if (validationResult.isFailure()) return validationResult;

        if (userRepository.userExists(credentials.username())) {
            var error = UserError.alreadyRegistered(username);
            return EmptyResult.failure(error);
        }

        var hashedPassword = hashService.hash(password);
        var user = new User(username, hashedPassword);
        userRepository.saveUser(user);

        return EmptyResult.success();
    }
}

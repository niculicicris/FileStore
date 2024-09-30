package me.niculicicris.filestore.service.authentication;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.error.UserError;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.data.dto.UserCredentialsDto;
import me.niculicicris.filestore.repository.abstraction.IAuthenticationRepository;
import me.niculicicris.filestore.repository.abstraction.IUserRepository;
import me.niculicicris.filestore.service.authentication.abstraction.IAuthenticationService;
import me.niculicicris.filestore.service.authentication.abstraction.IHashService;
import me.niculicicris.filestore.validation.abstraction.ICredentialsValidator;

public class AuthenticationService implements IAuthenticationService {
    private final IHashService hashService;
    private final ICredentialsValidator validator;
    private final IUserRepository userRepository;
    private final IAuthenticationRepository authenticationRepository;

    @Inject
    public AuthenticationService(IHashService hashService,
                                 ICredentialsValidator validator,
                                 IUserRepository userRepository,
                                 IAuthenticationRepository authenticationRepository) {
        this.hashService = hashService;
        this.validator = validator;
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public IEmptyResult loginUser(UserCredentialsDto credentials) {
        var username = credentials.username();
        var password = credentials.password();

        var validationResult = validator.validate(credentials);
        if (validationResult.isFailure()) return validationResult;

        var optionalUser = userRepository.getUser(credentials.username());
        if (optionalUser.isEmpty()) {
            var error = UserError.notFound(username);
            return EmptyResult.failure(error);
        }

        var user = optionalUser.get();
        if (!hashService.matches(user.password(), password)) {
            var error = UserError.failedAuthentication(username);
            return EmptyResult.failure(error);
        }

        authenticationRepository.saveAuthentication(username);

        return EmptyResult.success();
    }
}

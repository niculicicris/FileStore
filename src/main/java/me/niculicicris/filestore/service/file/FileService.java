package me.niculicicris.filestore.service.file;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.error.FileError;
import me.niculicicris.filestore.common.error.UserError;
import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.common.result.Result;
import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IResult;
import me.niculicicris.filestore.data.dto.FileDetailDto;
import me.niculicicris.filestore.data.dto.FileDto;
import me.niculicicris.filestore.data.model.StoredFile;
import me.niculicicris.filestore.repository.abstraction.IAuthenticationRepository;
import me.niculicicris.filestore.repository.abstraction.IFileRepository;
import me.niculicicris.filestore.service.file.abstraction.IFileService;
import me.niculicicris.filestore.validation.abstraction.IFileNameValidator;

import java.util.List;

public class FileService implements IFileService {
    private final IFileNameValidator validator;
    private final IAuthenticationRepository authenticationRepository;
    private final IFileRepository fileRepository;

    @Inject
    public FileService(IFileNameValidator validator,
                       IAuthenticationRepository authenticationRepository,
                       IFileRepository fileRepository) {
        this.validator = validator;
        this.authenticationRepository = authenticationRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public IEmptyResult storeFile(FileDto file) {
        var user = authenticationRepository.getAuthentication();

        if (user.isEmpty()) {
            var error = UserError.failedAuthorization();
            return EmptyResult.failure(error);
        }
        var owner = user.get();
        var name = file.name();

        if (fileRepository.fileExists(owner, name)) {
            var error = FileError.alreadyStored(name);
            return EmptyResult.failure(error);
        }

        var storedFile = new StoredFile(owner, name, file.content());
        fileRepository.saveFile(storedFile);

        return EmptyResult.success();
    }

    @Override
    public IResult<List<FileDetailDto>> getFilesDetails() {
        var user = authenticationRepository.getAuthentication();

        if (user.isEmpty()) {
            var error = UserError.failedAuthorization();
            return Result.failure(error);
        }

        var owner = user.get();
        var files = fileRepository.getFiles(owner);
        var details = files.stream()
                .map(file -> new FileDetailDto(file.name(), file.content().length))
                .toList();

        return Result.success(details);
    }

    @Override
    public IResult<FileDto> retrieveFile(String name) {
        var validationResult = validator.validate(name);

        if (validationResult.isFailure()) {
            return Result.failure(validationResult.getError());
        }
        var user = authenticationRepository.getAuthentication();

        if (user.isEmpty()) {
            var error = UserError.failedAuthorization();
            return Result.failure(error);
        }
        var optionalStoredFile = fileRepository.getFile(user.get(), name);

        if (optionalStoredFile.isEmpty()) {
            var error = FileError.notFound(name);
            return Result.failure(error);
        }
        var storedFile = optionalStoredFile.get();

        return Result.success(new FileDto(storedFile.name(), storedFile.content()));
    }

    @Override
    public IEmptyResult deleteFile(String name) {
        var validationResult = validator.validate(name);
        if (validationResult.isFailure()) return validationResult;

        var user = authenticationRepository.getAuthentication();

        if (user.isEmpty()) {
            var error = UserError.failedAuthorization();
            return EmptyResult.failure(error);
        }
        var owner = user.get();

        if (!fileRepository.fileExists(owner, name)) {
            var error = FileError.notFound(name);
            return EmptyResult.failure(error);
        }
        fileRepository.deleteFile(owner, name);

        return EmptyResult.success();
    }
}

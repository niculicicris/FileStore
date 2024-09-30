package me.niculicicris.filestore.service.file.abstraction;

import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IResult;
import me.niculicicris.filestore.data.dto.FileDetailDto;
import me.niculicicris.filestore.data.dto.FileDto;

import java.util.List;

public interface IFileService {
    IEmptyResult storeFile(FileDto file);

    IResult<List<FileDetailDto>> getFilesDetails();

    IResult<FileDto> retrieveFile(String name);
}

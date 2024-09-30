package me.niculicicris.filestore.service.file;

import me.niculicicris.filestore.common.result.EmptyResult;
import me.niculicicris.filestore.common.result.Result;
import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IResult;
import me.niculicicris.filestore.data.dto.FileDetailDto;
import me.niculicicris.filestore.data.dto.FileDto;
import me.niculicicris.filestore.service.file.abstraction.IFileService;

import java.util.ArrayList;
import java.util.List;

public class FileService implements IFileService {

    @Override
    public IEmptyResult storeFile(FileDto file) {
        return EmptyResult.success();
    }

    @Override
    public IResult<List<FileDetailDto>> getFilesDetails() {
        var details = new ArrayList<FileDetailDto>();

        details.add(new FileDetailDto("test.txt", 123));
        details.add(new FileDetailDto("pula.txt", 127));

        return Result.success(details);
    }

    @Override
    public IResult<FileDto> retrieveFile(String name) {
        return null;
    }
}

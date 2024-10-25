package me.niculicicris.filestore.service.file.abstraction;

import me.niculicicris.filestore.common.result.abstraction.IEmptyResult;
import me.niculicicris.filestore.common.result.abstraction.IResult;
import me.niculicicris.filestore.data.dto.FileDto;
import me.niculicicris.filestore.data.model.FileDescriptor;
import me.niculicicris.filestore.data.model.StoredFile;

import java.util.List;

public interface IFileService {
    IEmptyResult storeFile(FileDto file);

    IResult<List<FileDescriptor>> getFileDescriptors();

    IResult<StoredFile> getFile(String name);

    IEmptyResult deleteFile(String name);
}

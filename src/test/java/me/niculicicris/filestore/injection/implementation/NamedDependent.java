package me.niculicicris.filestore.injection.implementation;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.common.annotation.Named;
import me.niculicicris.filestore.injection.abstraction.IDependent;
import me.niculicicris.filestore.injection.abstraction.ITest;

public class NamedDependent implements IDependent {

    @Inject
    public NamedDependent(@Named("First") ITest first, @Named("Second") ITest second) {
    }
}

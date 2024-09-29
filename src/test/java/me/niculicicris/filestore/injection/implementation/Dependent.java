package me.niculicicris.filestore.injection.implementation;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.injection.abstraction.IDependent;
import me.niculicicris.filestore.injection.abstraction.ITest;

public class Dependent implements IDependent {

    @Inject
    public Dependent(ITest testClass) {
    }
}

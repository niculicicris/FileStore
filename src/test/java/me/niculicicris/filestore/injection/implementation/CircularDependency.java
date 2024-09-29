package me.niculicicris.filestore.injection.implementation;

import me.niculicicris.filestore.common.annotation.Inject;
import me.niculicicris.filestore.injection.abstraction.IDependent;
import me.niculicicris.filestore.injection.abstraction.ITest;

public class CircularDependency implements ITest {

    @Inject
    public CircularDependency(IDependent dependent) {

    }
}

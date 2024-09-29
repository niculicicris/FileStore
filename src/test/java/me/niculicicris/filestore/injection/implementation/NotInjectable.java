package me.niculicicris.filestore.injection.implementation;

import me.niculicicris.filestore.injection.abstraction.INotInjectable;
import me.niculicicris.filestore.injection.abstraction.ITest;

public class NotInjectable implements INotInjectable {

    public NotInjectable(ITest testClass) {
    }
}

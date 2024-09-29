package me.niculicicris.filestore.injection;

import me.niculicicris.filestore.common.exception.NoDefaultMappingException;
import me.niculicicris.filestore.common.exception.NoMappingException;
import me.niculicicris.filestore.injection.abstraction.ITest;
import me.niculicicris.filestore.injection.collection.MappingCollection;
import me.niculicicris.filestore.injection.implementation.TestClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MappingCollectionTest {
    private final MappingCollection mappingCollection = new MappingCollection();

    @Test
    public void getMapping_noDefaultImplementation_shouldThrowException() {
        assertThrows(
                NoDefaultMappingException.class,
                () -> mappingCollection.getMapping("Default", ITest.class)
        );
    }

    @Test
    public void getMapping_noImplementation_shouldThrowException() {
        assertThrows(
                NoMappingException.class,
                () -> mappingCollection.getMapping("TestKey", ITest.class)
        );
    }

    @Test
    public void getMapping_defaultKey_shouldReturnImplementation() {
        mappingCollection.addMapping(ITest.class, TestClass.class);

        var implementation = mappingCollection.getMapping("Default", ITest.class);
        assertEquals(TestClass.class, implementation);
    }

    @Test
    public void getMapping_specificKey_shouldReturnImplementation() {
        var key = "TestKey";

        mappingCollection.addMapping(key, ITest.class, TestClass.class);
        var implementation = mappingCollection.getMapping(key, ITest.class);

        assertEquals(TestClass.class, implementation);
    }
}

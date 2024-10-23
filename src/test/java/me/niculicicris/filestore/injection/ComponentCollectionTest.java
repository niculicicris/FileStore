package me.niculicicris.filestore.injection;

import me.niculicicris.filestore.common.exception.CircularDependencyException;
import me.niculicicris.filestore.common.exception.ImplementationConflictException;
import me.niculicicris.filestore.common.exception.NoInjectableConstructorException;
import me.niculicicris.filestore.common.injection.collection.ComponentCollection;
import me.niculicicris.filestore.common.injection.collection.MappingCollection;
import me.niculicicris.filestore.injection.abstraction.IDependent;
import me.niculicicris.filestore.injection.abstraction.INotInjectable;
import me.niculicicris.filestore.injection.abstraction.ITest;
import me.niculicicris.filestore.injection.implementation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ComponentCollectionTest {
    @Mock
    private MappingCollection mappingCollectionMock;

    @InjectMocks
    private ComponentCollection componentCollection;

    @Test
    public void addComponent_alreadyAdded_shouldThrowException() {
        componentCollection.addComponent(ITest.class, new TestClass());

        assertThrows(
                ImplementationConflictException.class,
                () -> componentCollection.addComponent(ITest.class, new TestClass())
        );
    }

    @Test
    public void getComponent_noInjectableConstructor_shouldThrowException() {
        when(mappingCollectionMock.getMapping("Default", INotInjectable.class))
                .thenAnswer(_ -> NotInjectable.class);

        assertThrows(
                NoInjectableConstructorException.class,
                () -> componentCollection.getComponent(INotInjectable.class)
        );
    }

    @Test
    public void getComponent_circularDependency_shouldThrowException() {
        when(mappingCollectionMock.getMapping("Default", IDependent.class))
                .thenAnswer(_ -> Dependent.class);
        when(mappingCollectionMock.getMapping("Default", ITest.class))
                .thenAnswer(_ -> CircularDependency.class);

        assertThrows(
                CircularDependencyException.class,
                () -> componentCollection.getComponent(IDependent.class)
        );
    }

    @Test
    public void getComponent_dependentClass_shouldCreateImplementation() {
        when(mappingCollectionMock.getMapping("Default", ITest.class))
                .thenAnswer(_ -> TestClass.class);
        when(mappingCollectionMock.getMapping("Default", IDependent.class))
                .thenAnswer(_ -> Dependent.class);

        var implementation = componentCollection.getComponent("Default", IDependent.class);

        assertInstanceOf(Dependent.class, implementation);
    }

    @Test
    public void getComponent_addedImplementation_shouldReturnImplementation() {
        componentCollection.addComponent(ITest.class, new TestClass());
        var implementation = componentCollection.getComponent(ITest.class);

        assertInstanceOf(TestClass.class, implementation);
    }

    @Test
    public void getComponent_defaultKey_shouldCreateImplementation() {
        when(mappingCollectionMock.getMapping("Default", ITest.class))
                .thenAnswer(_ -> TestClass.class);
        var implementation = componentCollection.getComponent(ITest.class);

        assertInstanceOf(TestClass.class, implementation);
    }

    @Test
    public void getComponent_specificKey_shouldCreateImplementation() {
        var key = "TestKey";

        when(mappingCollectionMock.getMapping(key, ITest.class))
                .thenAnswer(_ -> TestClass.class);
        var implementation = componentCollection.getComponent(key, ITest.class);

        assertInstanceOf(TestClass.class, implementation);
    }

    @Test
    public void getMapping_namedComponent_shouldReturnImplementation() {
        when(mappingCollectionMock.getMapping("First", ITest.class))
                .thenAnswer(_ -> TestClass.class);
        when(mappingCollectionMock.getMapping("Second", ITest.class))
                .thenAnswer(_ -> TestClass.class);
        when(mappingCollectionMock.getMapping("Default", IDependent.class))
                .thenAnswer(_ -> NamedDependent.class);

        var implementation = componentCollection.getComponent("Default", IDependent.class);

        assertInstanceOf(NamedDependent.class, implementation);
    }
}

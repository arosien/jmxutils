/**
 *  Copyright 2009 Martin Traverso
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.weakref.jmx.guice;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;

public class MBeanModule
        extends AbstractModule
{
    private ExportBuilder builder;

    @Override
    protected final void configure()
    {
        builder = newExporter(binder());

        install(new InternalMBeanModule());
        configureMBeans();
    }

    /**
     * To be overridden by subclasses. E.g.,
     *
     * protected void configureMBeans() {
     *    export(ManagedObject.class).as("test:name=X");
     *    export(ManagedObject.class).annotatedWith(SomeAnnotation.class).as("test:name=Y");
     * }
     *
     * When ExportBuilder is used, a raw MBeanModule can be imported to trigger the
     * registration of exported mbeans:
     *
     * Injector injector = Guice.createInjector(new MBeanModule(),
     *      new AbstractModule() {
     *          @Override
     *          protected void configure() {
     *              ExportBuilder builder = MBeanModule.newExporter();
     *              builder.export(AnotherManagedObject.class).as("test:name="Z");
     *          }
     *      });
     */
    protected void configureMBeans() {
    }

    protected AnnotatedExportBuilder export(Class<?> clazz)
    {
        return builder.export(clazz);
    }
    
    public static ExportBuilder newExporter(Binder binder) 
    {
    	return new ExportBuilder(newSetBinder(binder, Mapping.class));
    }

}

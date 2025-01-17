package org.apache.maven.plugin.coreit;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Loads classes and/or resources from the plugin class path and records the results in a properties file.
 *
  *
 * @author Benjamin Bentmann
 *
 */
@Mojo( name = "load", defaultPhase = LifecyclePhase.INITIALIZE )
public class LoadMojo
    extends AbstractLoadMojo
{

    /**
     * The path to the properties file used to track the results of the class/resource loading via the plugin class
     * loader.
     */
    @Parameter( property = "clsldr.pluginClassLoaderOutput" )
    private File pluginClassLoaderOutput;

    /**
     * The path to the properties file used to track the results of the class/resource loading via the thread's context
     * class loader.
     */
    @Parameter( property = "clsldr.contextClassLoaderOutput" )
    private File contextClassLoaderOutput;

    /**
     * Runs this mojo.
     *
     * @throws MojoExecutionException If the output file could not be created.
     */
    public void execute()
        throws MojoExecutionException
    {
        if ( pluginClassLoaderOutput != null )
        {
            execute( pluginClassLoaderOutput, getClass().getClassLoader() );
        }
        if ( contextClassLoaderOutput != null )
        {
            execute( contextClassLoaderOutput, Thread.currentThread().getContextClassLoader() );
        }
    }

}

package org.apache.maven.it;

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

import java.io.File;

import org.apache.maven.it.util.ResourceExtractor;

/**
 * This is a test for <a href="https://issues.apache.org/jira/browse/MNG-6972">MNG-6972</a>.
 */
public class MavenITmng6972AllowAccessToGraphPackageTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng6972AllowAccessToGraphPackageTest()
    {
        super( "[4.0.0-alpha-1,)" );
    }

    public void testitMNG6972()
        throws Exception
    {

        // The testdir is computed from the location of this file.
        final File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-6972-allow-access-to-graph-package" );

        Verifier verifier;

        /*
         * We must first make sure that any artifact created
         * by this test has been removed from the local
         * repository. Failing to do this could cause
         * unstable test results. Fortunately, the verifier
         * makes it easy to do this.
         */
        verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.deleteArtifact( "mng-6972-allow-access-to-graph-package", "build-plugin", "1.0", "jar" );
        verifier.deleteArtifact( "mng-6972-allow-access-to-graph-package", "using-module", "1.0", "jar" );

        verifier = new Verifier( new File( testDir.getAbsolutePath(), "build-plugin" ).getAbsolutePath() );
        verifier.getSystemProperties().put( "maven.multiModuleProjectDirectory", testDir.getAbsolutePath() );
        verifier.executeGoal( "install" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier = new Verifier( new File( testDir.getAbsolutePath(), "using-module" ).getAbsolutePath() );
        verifier.getSystemProperties().put( "maven.multiModuleProjectDirectory", testDir.getAbsolutePath() );
        verifier.executeGoal( "install" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }
}
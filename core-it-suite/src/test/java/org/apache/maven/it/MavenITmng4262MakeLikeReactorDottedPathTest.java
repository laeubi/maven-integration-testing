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

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-4262">MNG-4262</a>.
 * 
 * @author Benjamin Bentmann
 */
public class MavenITmng4262MakeLikeReactorDottedPathTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng4262MakeLikeReactorDottedPathTest()
    {
        super( "[3.0-alpha-3,)" ); 
    }

    private void clean( Verifier verifier )
        throws Exception
    {
        verifier.deleteDirectory( "target" );
        verifier.deleteDirectory( "../sub-a/target" );
    }

    /**
     * Verify that the project list can select the root project by its relative path ".".
     */
    public void testitMakeRoot()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-4262" );

        Verifier verifier = new Verifier( new File( testDir, "parent" ).getAbsolutePath() );
        verifier.setAutoclean( false );
        clean( verifier );
        verifier.getCliOptions().add( "-pl" );
        verifier.getCliOptions().add( "." );
        verifier.setLogFileName( "log-root.txt" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.assertFilePresent( "target/touch.txt" );
        verifier.assertFileNotPresent( "../sub-a/target/touch.txt" );
    }

    /**
     * Verify that the project list can select a sub module by a relative path like "../<something>".
     */
    public void testitMakeModule()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-4262" );

        Verifier verifier = new Verifier( new File( testDir, "parent" ).getAbsolutePath() );
        verifier.setAutoclean( false );
        clean( verifier );
        verifier.getCliOptions().add( "-pl" );
        verifier.getCliOptions().add( "../sub-a" );
        verifier.setLogFileName( "log-module.txt" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.assertFileNotPresent( "target/touch.txt" );
        verifier.assertFilePresent( "../sub-a/target/touch.txt" );
    }

}
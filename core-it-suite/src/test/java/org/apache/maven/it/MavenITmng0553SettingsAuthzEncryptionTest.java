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

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MavenITmng0553SettingsAuthzEncryptionTest
    extends AbstractMavenIntegrationTestCase
{
    public MavenITmng0553SettingsAuthzEncryptionTest()
    {
        // TODO: reintroduce for 3.0
        super( "(2.1.0-M1,3.0-alpha-1)" ); // 2.1.0-M2+
    }

    /**
     * Test that the encrypted auth infos given in the settings.xml are decrypted.
     */
    public void testit0553()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-0553" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        List cliOptions = new ArrayList();
        cliOptions.add( "--settings" );
        cliOptions.add( "settings.xml" );
        verifier.getSystemProperties().setProperty( "maven.sec.path", "settings-security.xml" );
        verifier.setCliOptions( cliOptions );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        Properties props = verifier.loadProperties( "target/auth.properties" );
        assertEquals( "testuser", props.getProperty( "test.username" ) );
        assertEquals( "testtest", props.getProperty( "test.password" ) );
    }

}
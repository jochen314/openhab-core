/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.config.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.eclipse.smarthome.config.core.ConfigDescriptionParameter.Type;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ConfigDescriptionBuilder) class.
 *
 * @author Christoph Weitkamp - Initial contribution
 */
public class ConfigDescriptionBuilderTest {

    private static final URI CONFIG_URI = URI.create("system:ephemeris");
    private static final ConfigDescriptionParameter PARAM1 = ConfigDescriptionParameterBuilder
            .create("TEST PARAM 1", Type.TEXT).build();
    private static final ConfigDescriptionParameter PARAM2 = ConfigDescriptionParameterBuilder
            .create("TEST PARAM 2", Type.INTEGER).build();
    private static final ConfigDescriptionParameterGroup GROUP1 = new ConfigDescriptionParameterGroup("TEST GROUP 1",
            null, false, "Test Group 1", null);
    private static final ConfigDescriptionParameterGroup GROUP2 = new ConfigDescriptionParameterGroup("TEST GROUP 2",
            null, true, "Test Group 2", null);
    private ConfigDescriptionBuilder builder;

    @Before
    public void setup() {
        builder = ConfigDescriptionBuilder.create(CONFIG_URI);
    }

    @Test
    public void testWithoutParametersAndGroups() {
        ConfigDescription configDescription = builder.build();
        assertThat(configDescription.getUID(), is(CONFIG_URI));
        assertThat(configDescription.getParameterGroups(), hasSize(0));
        assertThat(configDescription.getParameters(), hasSize(0));
    }

    @Test
    public void testWithOneParameter() {
        ConfigDescription configDescription = builder.withParameter(PARAM1).build();
        assertThat(configDescription.getUID(), is(CONFIG_URI));
        assertThat(configDescription.getParameterGroups(), hasSize(0));
        assertThat(configDescription.getParameters(), hasSize(1));
        assertThat(configDescription.getParameters().get(0), is(PARAM1));
    }

    @Test
    public void testWithTwoParameters() {
        final List<ConfigDescriptionParameter> params = Arrays.asList(PARAM1, PARAM2);

        ConfigDescription configDescription = builder.withParameters(params).build();
        assertThat(configDescription.getUID(), is(CONFIG_URI));
        assertThat(configDescription.getParameterGroups(), hasSize(0));
        assertThat(configDescription.getParameters(), hasSize(2));
        assertThat(configDescription.getParameters(), is(params));
    }

    @Test
    public void testWithOneParameterGroup() {
        ConfigDescription configDescription = builder.withParameterGroup(GROUP1).build();
        assertThat(configDescription.getUID(), is(CONFIG_URI));
        assertThat(configDescription.getParameterGroups(), hasSize(1));
        assertThat(configDescription.getParameterGroups().get(0), is(GROUP1));
        assertThat(configDescription.getParameters(), hasSize(0));
    }

    @Test
    public void testWithTwoParameterGroups() {
        final List<ConfigDescriptionParameterGroup> groups = Arrays.asList(GROUP1, GROUP2);

        ConfigDescription configDescription = builder.withParameterGroups(groups).build();
        assertThat(configDescription.getUID(), is(CONFIG_URI));
        assertThat(configDescription.getParameterGroups(), hasSize(2));
        assertThat(configDescription.getParameterGroups(), is(groups));
        assertThat(configDescription.getParameters(), hasSize(0));
    }
}

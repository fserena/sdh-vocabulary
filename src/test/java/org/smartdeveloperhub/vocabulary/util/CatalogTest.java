/**
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   This file is part of the Smart Developer Hub Project:
 *     http://www.smartdeveloperhub.org/
 *
 *   Center for Open Middleware
 *     http://www.centeropenmiddleware.com/
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Copyright (C) 2015-2016 Center for Open Middleware.
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Artifact    : org.smartdeveloperhub.vocabulary:sdh-vocabulary:0.3.0-SNAPSHOT
 *   Bundle      : sdh-vocabulary-0.3.0-SNAPSHOT.jar
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.vocabulary.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class CatalogTest {

	@Rule
	public TestName name=new TestName();

	private static final Path ROOT = Paths.get("src/test/resources/vocabulary");
	private static final URI BASE = URI.create("http://www.smartdeveloperhub.org/vocabulary/");
	private static final Context CONTEXT = Context.create(BASE, ROOT);

	private Catalog createSut() {
		final Catalog sut=new Catalog(CONTEXT);
		return sut;
	}

	private Path moduleLocation(final String name) {
		return ROOT.resolve(name);
	}

	private void showResult(final Result<Module> result) {
		System.out.println(this.name.getMethodName());
		System.out.printf("Result:%n%s%n",result);
	}

	@Test
	public void testLoadModule$anonymousOntology() throws Exception {
		final Catalog sut = createSut();
		final Result<Module> result = sut.loadModule(moduleLocation("anon_ontology.ttl"));
		showResult(result);
		assertThat(result.isAvailable(),equalTo(false));
	}

	@Test
	public void testLoadModule$badVersionInfo() throws Exception {
		final Catalog sut = createSut();
		final Result<Module> result = sut.loadModule(moduleLocation("versioned_module_with_bad_version_info.ttl"));
		showResult(result);
		assertThat(result.isAvailable(),equalTo(true));
	}

	@Test
	public void testLoadModule$multipleVersionInfo() throws Exception {
		final Catalog sut = createSut();
		final Result<Module> result = sut.loadModule(moduleLocation("versioned_module_with_multiple_version_info.ttl"));
		showResult(result);
		assertThat(result.isAvailable(),equalTo(true));
	}

	@Test
	public void testLoadModule$versionInfoNotAllowed() throws Exception {
		final Catalog sut = createSut();
		final Result<Module> result = sut.loadModule(moduleLocation("unversioned_module_with_version_info.ttl"));
		showResult(result);
		assertThat(result.isAvailable(),equalTo(true));
	}

}
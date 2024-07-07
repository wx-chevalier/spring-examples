package com.taotao.cloud.maven.plugin;

import java.io.File;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "test-dummy")
public class DummyMojo {

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	/**
	 * Hello World.
	 */
	@Parameter(defaultValue = "${project.build.outputDirectory}")
	private File outputDirectory;
}

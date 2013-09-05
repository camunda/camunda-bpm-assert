package org.camunda.bpm.engine.impl.fluent;

import java.io.InputStream;
import java.util.Date;
import java.util.zip.ZipInputStream;

import org.camunda.bpm.engine.fluent.AbstractFluentProcessEngineAware;
import org.camunda.bpm.engine.fluent.FluentDeployment;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;

public class FluentDeploymentImpl extends AbstractFluentProcessEngineAware implements FluentDeployment {

    private final DeploymentBuilder deploymentBuilder;

    public FluentDeploymentImpl(final FluentProcessEngine engine) {
        super(engine);
        this.deploymentBuilder = engine.getRepositoryService().createDeployment();
    }

    @Override
    public FluentDeployment addClasspathResource(final String... resources) {
        for (final String resource : resources) {
            deploymentBuilder.addClasspathResource(resource);
        }
        return this;
    }

    @Override
    public FluentDeployment name(final String name) {
        deploymentBuilder.name(name);
        return this;
    }

    @Override
    public FluentDeployment addInputStream(final String resourceName, final InputStream inputStream) {
        deploymentBuilder.addInputStream(resourceName, inputStream);
        return this;
    }

    @Override
    public FluentDeployment addString(final String resourceName, final String text) {
        deploymentBuilder.addString(resourceName, text);
        return this;
    }

    @Override
    public FluentDeployment addZipInputStream(final ZipInputStream zipInputStream) {
        deploymentBuilder.addZipInputStream(zipInputStream);
        return this;
    }

    @Override
    public FluentDeployment enableDuplicateFiltering() {
        deploymentBuilder.enableDuplicateFiltering();
        return this;
    }

    @Override
    public FluentDeployment activateProcessDefinitionsOn(final Date date) {
        deploymentBuilder.activateProcessDefinitionsOn(date);
        return this;
    }

    @Override
    public String deploy() {
        final Deployment deploy = deploymentBuilder.deploy();
        return deploy.getId();
    }

    @Override
    public DeploymentBuilder getDelegate() {
        return deploymentBuilder;
    }

}

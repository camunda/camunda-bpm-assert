package org.camunda.bpm.engine.fluent;

import java.io.InputStream;
import java.util.Date;
import java.util.zip.ZipInputStream;

import org.camunda.bpm.engine.repository.DeploymentBuilder;

/**
 * @author Jan Galinski, Holisticon AG
 */
public interface FluentDeployment extends FluentDelegate<DeploymentBuilder> {

  /**
   * Delegates to {@link DeploymentBuilder#addClasspathResource(String)}.
   * 
   * @param resources
   *          vararg list of bpmn file-paths in classpath
   * @return this instance for fluent use
   */
  FluentDeployment addClasspathResource(final String... resources);

  /**
   * Performs deployment and returns the new deploymentId. Delegates to
   * {@link DeploymentBuilder#deploy()}.
   * 
   * @return id of finished deployment
   */
  String deploy();

  /**
   * Delegates to {@link DeploymentBuilder#name(String)}.
   * 
   * @param name
   *          name for deployment to identify
   * @return this instance for fluent use
   */
  FluentDeployment name(final String name);

  /**
   * Delegates to {@link DeploymentBuilder#activateProcessDefinitionsOn(Date)}.
   * 
   * @param date
   *          activation date
   * @return this instance for fluent use
   */
  FluentDeployment activateProcessDefinitionsOn(final Date date);

  /**
   * Delegates to {@link DeploymentBuilder#enableDuplicateFiltering()}.
   * 
   * @return this instance for fluent use
   */
  FluentDeployment enableDuplicateFiltering();

  /**
   * Delegates to {@link DeploymentBuilder#addZipInputStream(ZipInputStream)}.
   * 
   * @param zipInputStream
   *          zip archive stream with process to deploy
   * @return this instance for fluent use
   */
  FluentDeployment addZipInputStream(final ZipInputStream zipInputStream);

  /**
   * Delegates to {@link DeploymentBuilder#addString(String, String)}.
   * 
   * @param resourceName
   *          name for the given bpmn resource to create
   * @param text
   *          bpmn content to deploy
   * @return this instance for fluent use
   */
  FluentDeployment addString(final String resourceName, final String text);

  /**
   * Delegates to {@link DeploymentBuilder#addInputStream(String, InputStream)}.
   * 
   * @param resourceName
   *          name for the given bpmn resource to create
   * @param inputStream
   *          stream containing bpmn content to deploy
   * @return this instance for fluent use
   */
  FluentDeployment addInputStream(final String resourceName, final InputStream inputStream);

}

package vrampal.connectfour.core.impl;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.StoryReporterBuilder.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 * JBehave glue to run a story inside JUnit.
 */
public class BoardStory extends JUnitStory {

  @Override
  public Configuration configuration() {
    // TODO better config declaration
    StoryReporterBuilder reporterBuilder = new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE,
        Format.TXT);

    LoadFromClasspath storyLoader = new LoadFromClasspath(this.getClass());

    Configuration config = new MostUsefulConfiguration().useStoryLoader(storyLoader).useStoryReporterBuilder(
        reporterBuilder);

    return config;
  }

  @Override
  public InjectableStepsFactory stepsFactory() {
    return new InstanceStepsFactory(configuration(), new BoardStorySteps());
  }

}

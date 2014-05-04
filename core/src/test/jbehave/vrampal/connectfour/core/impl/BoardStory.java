package vrampal.connectfour.core.impl;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.net.URL;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 * JBehave glue to run a story inside JUnit.
 */
public class BoardStory extends JUnitStory {

  @Override
  public Configuration configuration() {

    LoadFromClasspath storyLoader = new LoadFromClasspath(this.getClass());
    URL codeLocation = codeLocationFromClass(this.getClass());

    // TODO test ANSI_CONSOLE and IDE_CONSOLE format
    StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
        .withCodeLocation(codeLocation)
        .withDefaultFormats()
        .withFormats(CONSOLE, TXT, HTML, XML);

    Configuration config = new MostUsefulConfiguration()
        .useStoryLoader(storyLoader)
        .useStoryReporterBuilder(reporterBuilder);

    return config;
  }

  @Override
  public InjectableStepsFactory stepsFactory() {
    return new InstanceStepsFactory(configuration(), new BoardStorySteps());
  }

}

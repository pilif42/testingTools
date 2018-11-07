# To create the wrapper stuff
gradle wrapper


# To run unit tests only (report at /build/reports/test)
./gradlew clean test
or
./gradlew clean build -x integrationTest


# To run integration tests only (report at /build/reports/integrationTest)
./gradlew clean integrationTest
or
./gradlew clean build -x test


# To run unit & integration tests
./gradlew clean test integrationTest
or
./gradlew clean build


TODO 1
gradle wrapper --warning-mode all
> Configure project :
Gradle now uses separate output directories for each JVM language, but this build assumes a single directory for all classes from a source set. This behaviour has been deprecated and is scheduled to be removed in Gradle 5.0.
        at build_4ffcjyojtvzco4cfsui5ud41x$_run_closure7.doCall(/home/philippe/code/testingTools/build.gradle:73)
        (Run with --stacktrace to get the full stack trace of this deprecation warning.)
The setTestClassesDir(File) method has been deprecated. This is scheduled to be removed in Gradle 5.0. Please use the setTestClassesDirs(FileCollection) method instead.
        at build_4ffcjyojtvzco4cfsui5ud41x$_run_closure7.doCall(/home/philippe/code/testingTools/build.gradle:73)
        (Run with --stacktrace to get the full stack trace of this deprecation warning.)


TODO 2
Tempo measure: run DummyServiceTest with @RunWith(MockitoJUnitRunner.Silent.class)
org.mockito.exceptions.misusing.UnnecessaryStubbingException:
Unnecessary stubbings detected in test class: DummyServiceTest
Clean & maintainable test code requires zero unnecessary code.
Following stubbings are unnecessary (click to navigate to relevant line of code):
  1. -> at com.mysample.utils.LoggingAssertion.givenLoggingMonitored(LoggingAssertion.java:26)
Please remove unnecessary stubbings or use 'lenient' strictness. More info: javadoc for UnnecessaryStubbingException class.
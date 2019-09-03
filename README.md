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


# To get around the defect in IntelliJ IDEA 2019.2.1 where unit tests do not execute at times
File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle -> Run tests using
Switch from Gradle (default) to IntelliJ IDEA.


# TODO 
Fix ConsumerFactoryTest.

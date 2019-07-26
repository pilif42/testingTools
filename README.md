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
./gradlew clean build --warning-mode all
Detecting annotation processors on the compile classpath has been deprecated. Gradle 5.0 will ignore annotation processors on the compile classpath. The following annotation processors were detected on the compile classpath: 'lombok.launch.AnnotationProcessorHider$AnnotationProcessor' and 'lombok.launch.AnnotationProcessorHider$ClaimingProcessor'.  Please add them to the annotation processor path instead. If you did not intend to use annotation processors, you can use the '-proc:none' compiler argument to ignore them.
--> solution at https://discuss.gradle.org/t/regarding-the-annotation-processors-on-compile-classpath-warning-in-gradle-4-6/26144/5


TODO Powermock
At the class level:
@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractDynamicEventHBaseBolt.class)
In PrepareForTest, you give the class name of the class in which you do a new HBaseWriteManager(…)

Inside the class:
@Mock
private HBaseWriteManager hBaseWriteManager;

@Before
public void setUp() throws Exception {
    whenNew(HBaseWriteManager.class).withAnyArguments().thenReturn(hBaseWriteManager);

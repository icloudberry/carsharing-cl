package icloudberry.carsharing.app;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class AppTest {
    @Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }

    @Test
    public void testGetCheapest() {
        assertNotNull("app should return result", App.getCheapestCarsharing(20, 60, false));
    }
}

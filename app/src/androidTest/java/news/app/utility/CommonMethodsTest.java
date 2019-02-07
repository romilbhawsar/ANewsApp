package news.app.utility;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommonMethodsTest {

    @Test
    public void isInternetConnected() {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals(true,CommonMethods.isInternetConnected(appContext));
    }
}
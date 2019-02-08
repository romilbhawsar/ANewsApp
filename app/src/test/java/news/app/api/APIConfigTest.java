package news.app.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class APIConfigTest {

    @Test
    public void getRetrofit() {

        assertNotEquals(null,APIConfig.getRetrofit());
    }
}
package ro.store.management.tool.exception;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandlerTest {

    @Test
    public void handleException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Exception exception = new Exception();
        ResponseEntity<String> result = globalExceptionHandler.handleException(exception);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getStatusCode());
        Assert.assertEquals(500, result.getStatusCode().value());
        Assert.assertNotNull(result.getBody());
        Assert.assertTrue(result.getBody().length() > 0);
    }

    @Test
    public void handleRuntimeException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        RuntimeException exception = new RuntimeException();
        ResponseEntity<String> result = globalExceptionHandler.handleException(exception);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getStatusCode());
        Assert.assertEquals(500, result.getStatusCode().value());
        Assert.assertNotNull(result.getBody());
        Assert.assertTrue(result.getBody().length() > 0);
    }
}

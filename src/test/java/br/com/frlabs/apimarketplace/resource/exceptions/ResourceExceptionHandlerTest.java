package br.com.frlabs.apimarketplace.resource.exceptions;

import br.com.frlabs.apimarketplace.services.exceptions.DataIntegratyViolationException;
import br.com.frlabs.apimarketplace.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;

    @Test
    void when_objectNotFound_thenReturn_responseEntityStandartError() {
        ResponseEntity<StandartError> response = resourceExceptionHandler.objectNotFound(
                new ObjectNotFoundException("Object not found!!"),
                new MockHttpServletRequest()
        );
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandartError.class, response.getBody().getClass());
        assertEquals("Object not found!!", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void when_dataIntegraty_thenReturn_responseEntityStandartError() {
        ResponseEntity<StandartError> response = resourceExceptionHandler.dataIntegratyViolationException(
                new DataIntegratyViolationException(""),
                new MockHttpServletRequest()
        );
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandartError.class, response.getBody().getClass());
        assertEquals("Data Integraty violation!!", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

}
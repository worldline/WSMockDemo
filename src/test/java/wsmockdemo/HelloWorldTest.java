package wsmockdemo;

import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class HelloWorldTest {
	
	@Mock
	ServiceInterface serviceMock;

	
	// this is a direct test without using mock
	@Test
	public void testAddOldWay() throws Exception {
		String url = "http://localhost:8081/ws";
		Endpoint endpoint = Endpoint.publish(url, new ServiceImpl());
		
		ServiceInterface client = ServiceClient.createHelloWorldServiceClient(url);

		int res = client.add(3, 2);
		endpoint.stop();
		Assert.assertEquals(5, res);
	}

	
	// this is the test using mock, the real implementation is not called here
	@Test
	public void testAddWithMock() throws Exception {
		
		
		// wrap it in proxy
		ServiceInterface serviceInterface = ServiceProxy.newInstance(serviceMock);
		
		// publish the mock 
		String url = "http://localhost:8081/ws";
		Endpoint endpoint = Endpoint.publish(url, serviceInterface);
		
		// define mock behavior
		Mockito.when(serviceMock.add(2, 3)).thenReturn(5);
		
		// call mock
		ServiceInterface client = ServiceClient.createHelloWorldServiceClient(url);
		int res = client.add(2, 3);
		endpoint.stop();
		
		// assertions
		Assert.assertEquals(5, res);
		Mockito.verify(serviceMock).add(2, 3);
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
}

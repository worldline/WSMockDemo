package wsmockdemo;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;

import junit.framework.Assert;
import net.atos.xa.resourcelocator.ResourceLocator;

import org.apache.cxf.jaxws.EndpointImpl;
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
		org.apache.cxf.jaxws.EndpointImpl endpoint = (EndpointImpl) Endpoint.create(serviceInterface);
		QName serviceName = new QName("htt://www.atos.net", "HelloWorldService");
		endpoint.setServiceName(serviceName);
		endpoint.publish(url);
		
		// define mock behavior
		Mockito.when(serviceMock.add(2, 3)).thenReturn(5);
		
		// call mock

		try {
			ServiceInterface client = ResourceLocator.lookup(ServiceInterface.class);
			
			int res = client.add(2, 3);
			Assert.assertEquals(5, res);
		} finally {
			endpoint.stop();
		}
		

		Mockito.verify(serviceMock).add(2, 3);
		
		
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
}

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

	@Test
	public void testAddOldWay() throws Exception {
		String url = "http://localhost:8081/ws";
		Endpoint endpoint = Endpoint.publish(url, new ServiceImpl());
		int res = new HelloWorldImpl(url).add(3, 2);
		endpoint.stop();
		Assert.assertEquals(5, res);
	}

	@Test
	public void testAddWithMock() throws Exception {
		String url = "http://localhost:8081/ws";
		Mockito.when(serviceMock.add(2, 3)).thenReturn(5);
		ServiceInterface serviceInterface = ServiceProxy.newInstance(serviceMock);
		Endpoint endpoint = Endpoint.publish(url, serviceInterface);
		int res = new HelloWorldImpl(url).add(2, 3);
		endpoint.stop();
		Assert.assertEquals(5, res);
		Mockito.verify(serviceMock.add(2, 3));
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
}

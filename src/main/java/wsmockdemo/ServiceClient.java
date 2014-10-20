package wsmockdemo;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class ServiceClient {
	public static ServiceInterface createHelloWorldServiceClient(String address) {
		// we use CXF to create a client for us as its easier than JAXWS and
		// works
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(ServiceInterface.class);
		factory.setAddress(address);
		return (ServiceInterface) factory.create();
	}

}

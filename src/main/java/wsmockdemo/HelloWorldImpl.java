package wsmockdemo;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

/**
 * This is the class we want to test, it relies on a webservice.
 */
public class HelloWorldImpl {
	private ServiceInterface service;

	public HelloWorldImpl(String url) throws MalformedURLException {
		QName portName = new QName("urn:net:atos:demo", "ServiceImplPort");
		QName serviceName = new QName("urn:net:atos:demo", "ServiceImplService");
		service = Service.create(new URL(url), serviceName).getPort(portName, ServiceInterface.class);
	}

	public int add(int a, int b) {
		return service.add(a, b);
	}

	public static void main(String[] args) throws Exception {
		String url = "http://localhost:8081/ws";
		Endpoint endpoint = Endpoint.publish(url, new ServiceImpl());
		System.out.println(new HelloWorldImpl(url).add(3, 2));
		endpoint.stop();
	}
}

package wsmockdemo;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "urn:net:atos:demo")
public class ServiceImpl implements ServiceInterface {

	@WebMethod
	@WebResult(name = "output", targetNamespace = "urn:net:atos:demo")
	public Integer add(Integer a, Integer b) {
		return a + b;
	}

}

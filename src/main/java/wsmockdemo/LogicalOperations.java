package wsmockdemo;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import net.atos.xa.resourcelocator.ResourceLocator;

/**
 * This is the class we want to test, it relies on a webservice.
 */
public class LogicalOperations {
	private EvaluatorServiceInterface evaluator;


	// this web class is depending on external web services for evaluating given string.
	// in reality this would be injected instead of being looked up locally
	public LogicalOperations() throws MalformedURLException {
		evaluator = ResourceLocator.lookup(EvaluatorServiceInterface.class);
	}

	
	public boolean and(String a, String b) {
		if (!evaluator.evaluate(a))
			return false;
		return evaluator.evaluate(b);
	}

}

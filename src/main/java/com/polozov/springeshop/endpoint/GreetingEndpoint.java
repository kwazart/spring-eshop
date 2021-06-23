package com.polozov.springeshop.endpoint;

import com.polozov.springeshop.config.WebServiceConfig;
import com.polozov.springeshop.service.GreetingService;
import com.polozov.springeshop.ws.greeting.GetGreetingRequest;
import com.polozov.springeshop.ws.greeting.GetGreetingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class GreetingEndpoint {

	public static final String NAMESPACE_URL = "http://polozov.com/springeshop/ws/greeting";

	private GreetingService greetingService;

	@Autowired
	public GreetingEndpoint(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@PayloadRoot(namespace = NAMESPACE_URL, localPart = "getGreetingRequest")
	@ResponsePayload
	public GetGreetingResponse getGreeting(@RequestPayload GetGreetingRequest request)
			throws DatatypeConfigurationException {
		GetGreetingResponse response = new GetGreetingResponse();

		response.setGreeting(greetingService.generateGreeting(request.getName()));

		return response;
	}
}
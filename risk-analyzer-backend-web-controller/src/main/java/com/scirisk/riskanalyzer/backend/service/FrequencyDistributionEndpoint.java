package com.scirisk.riskanalyzer.backend.service;

import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FrequencyDistributionEndpoint {

	@Autowired
	FrequencyDistributionService frequencyDistributionService;
	RequestMarshaller requestMarshaller = new RequestMarshallerJDomImpl();
	ResponseMarshaller responseMarshaller = new ResponseMarshallerJDomIMpl();

	@PayloadRoot(namespace = "http://scirisk.com/xml/ns/risk-analyzer", localPart = "CalculateRequest")
	public @ResponsePayload
	StreamSource frequencyDistribution(@RequestPayload StreamSource request) throws Exception {
		CalculateRequest calculateRequest = requestMarshaller.unmarshall(request);

		CalculateResponse calculateResponse = frequencyDistributionService.calculate(calculateRequest);
		StreamSource response = responseMarshaller.marshall(calculateResponse);
		return response;
	}

}

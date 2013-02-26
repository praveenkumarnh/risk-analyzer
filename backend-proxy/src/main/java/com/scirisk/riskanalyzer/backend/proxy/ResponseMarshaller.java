package com.scirisk.riskanalyzer.backend.proxy;

import java.io.IOException;

import javax.xml.transform.stream.StreamSource;


public interface ResponseMarshaller {
	StreamSource marshall(CalculateResponse response) throws IOException;

	CalculateResponse unmarshall(StreamSource source) throws IOException;
}
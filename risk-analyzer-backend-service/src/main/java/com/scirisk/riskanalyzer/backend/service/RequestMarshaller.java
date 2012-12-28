package com.scirisk.riskanalyzer.backend.service;

import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

public interface RequestMarshaller {

	StreamSource marshall(CalculateRequest request) throws IOException;

	CalculateRequest unmarshall(StreamSource source) throws IOException;

}
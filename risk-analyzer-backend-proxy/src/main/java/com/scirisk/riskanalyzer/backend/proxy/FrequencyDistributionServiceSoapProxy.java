package com.scirisk.riskanalyzer.backend.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.scirisk.riskanalyzer.backend.service.CalculateRequest;
import com.scirisk.riskanalyzer.backend.service.CalculateResponse;
import com.scirisk.riskanalyzer.backend.service.FrequencyDistributionService;
import com.scirisk.riskanalyzer.backend.service.RequestMarshaller;
import com.scirisk.riskanalyzer.backend.service.ResponseMarshaller;

public class FrequencyDistributionServiceSoapProxy implements FrequencyDistributionService {

	private String endpointUrl;

	public FrequencyDistributionServiceSoapProxy(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public CalculateResponse calculate(CalculateRequest request)
			throws Exception {

		WebServiceTemplate template = new WebServiceTemplate();
		Element calculateRequestElm = new RequestMarshaller().marshall(request);

		StreamSource source = createStreamSource(calculateRequestElm);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(out);

		template.sendSourceAndReceiveToResult(this.endpointUrl, source, result);

		System.out.println(new String(out.toByteArray()));
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new ByteArrayInputStream(out
				.toByteArray()));

		return new ResponseMarshaller().unmarshall(document.getRootElement());
	}

	private StreamSource createStreamSource(Element document)
			throws IOException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(document, buffer);

		StreamSource source = new StreamSource(new ByteArrayInputStream(
				buffer.toByteArray()));

		return source;
	}

}

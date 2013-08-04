package com.danielpacak.riskanalyzer.frontend.web.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionNetworkRepository;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkParser;

public class NetworkControllerTest {

	@Test
	public void testGetNetworkForGoogleMap() throws Exception {
		NetworkController controller = new NetworkController();
		controller.networkManager = mock(DistributionNetworkRepository.class);
		DistributionNetwork network = new DistributionNetwork();
		when(controller.networkManager.read()).thenReturn(network);

		Assert.assertEquals(network, controller.getNetworkForGoogleMap());
		verify(controller.networkManager).read();
	}

	@Test
	public void testImportFromXmlWithNonEmptyFile() throws Exception {
		NetworkController controller = new NetworkController();
		controller.networkParser = mock(NetworkParser.class);
		controller.networkManager = mock(DistributionNetworkRepository.class);

		MultipartFile networkXml = mock(MultipartFile.class);
		when(networkXml.isEmpty()).thenReturn(false);
		InputStream inputStream = mock(InputStream.class);
		when(networkXml.getInputStream()).thenReturn(inputStream);
		DistributionNetwork network = mock(DistributionNetwork.class);
		when(controller.networkParser.parse(inputStream)).thenReturn(network);

		HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		PrintWriter writer = mock(PrintWriter.class);
		when(httpResponse.getWriter()).thenReturn(writer);

		controller.importFromXml(networkXml, httpResponse);

		verify(controller.networkParser).parse(inputStream);
		verify(controller.networkManager).save(network);
		// FIXME This text/html content type is probably a bug in ExtJS 4
		verify(httpResponse).setContentType("text/html");
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		verify(writer).println(jsonResponse.toString(2));
	}

	@Test
	public void testImportFromXmlWithEmptyFile() throws Exception {
		NetworkController controller = new NetworkController();

		MultipartFile networkXml = mock(MultipartFile.class);
		when(networkXml.isEmpty()).thenReturn(true);

		HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		PrintWriter writer = mock(PrintWriter.class);
		when(httpResponse.getWriter()).thenReturn(writer);

		controller.importFromXml(networkXml, httpResponse);
		// FIXME This text/html content type is probably a bug in ExtJS 4
		verify(httpResponse).setContentType("text/html");
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", false);
		verify(writer).println(jsonResponse.toString(2));
	}

}
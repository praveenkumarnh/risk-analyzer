package com.scirisk.riskanalyzer.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.model.NetworkParser;
import com.scirisk.riskanalyzer.persistence.NetworkManager;

public class NetworkControllerTest {

	@Test
	public void testGetNetworkForGoogleMap() throws Exception {
		NetworkController controller = new NetworkController();
		controller.networkManager = mock(NetworkManager.class);
		Network network = new Network();
		when(controller.networkManager.read()).thenReturn(network);

		Assert.assertEquals(network, controller.getNetworkForGoogleMap());
		verify(controller.networkManager).read();
	}

	@Test
	public void testImportFromXmlWithNonEmptyFile() throws Exception {
		NetworkController controller = new NetworkController();
		controller.networkParser = mock(NetworkParser.class);
		controller.networkManager = mock(NetworkManager.class);

		MultipartFile networkXml = mock(MultipartFile.class);
		when(networkXml.isEmpty()).thenReturn(false);
		InputStream inputStream = mock(InputStream.class);
		when(networkXml.getInputStream()).thenReturn(inputStream);
		Network network = mock(Network.class);
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

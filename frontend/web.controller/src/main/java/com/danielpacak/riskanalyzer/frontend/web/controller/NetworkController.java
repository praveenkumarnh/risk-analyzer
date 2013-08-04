package com.danielpacak.riskanalyzer.frontend.web.controller;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionNetworkRepository;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkMarshaller;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkParser;

@Controller
public class NetworkController {

	@Autowired
	DistributionNetworkRepository networkManager;

	@Autowired
	private NetworkMarshaller networkMarshaller;

	@Autowired
	NetworkParser networkParser;

	@RequestMapping(value = "/network/map", method = RequestMethod.GET)
	public @ResponseBody
	DistributionNetwork getNetworkForGoogleMap() throws Exception {
		return networkManager.read();
	}

	@RequestMapping(value = "/network/tree", method = RequestMethod.GET)
	public void getNetworkForTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		DistributionNetwork network = networkManager.read();

		Collection<Facility> nodes = network.getNodes();
		Collection<DistributionChannel> edges = network.getEdges();

		JSONObject nodeFolder = new JSONObject();
		nodeFolder.element("text", "Facility");
		nodeFolder.element("cls", "folder");
		nodeFolder.element("expanded", true);
		nodeFolder.element("children", nodesToJson(nodes));

		JSONObject edgeFolder = new JSONObject();
		edgeFolder.element("text", "Distribution Channel");
		edgeFolder.element("cls", "folder");
		edgeFolder.element("expanded", true);
		edgeFolder.element("children", edgesToJson(edges));

		JSONArray networkChildren = new JSONArray();
		networkChildren.add(nodeFolder);
		networkChildren.add(edgeFolder);

		JSONObject networkJson = new JSONObject();
		networkJson.element("text", "Distribution Network");
		networkJson.element("cls", "folder");
		networkJson.element("expanded", true);
		networkJson.element("children", networkChildren);

		JSONArray root = new JSONArray();
		root.add(networkJson);

		response.getWriter().print(root.toString());
	}

	@RequestMapping(value = "/network.xml", method = RequestMethod.GET)
	public void exportToXml(HttpServletRequest request, HttpServletResponse response) throws Exception {

		DistributionNetwork network = networkManager.read();
		response.setContentType("application/xml");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String file = "risk-analyzer-network-export-" + dateFormat.format(new Date()) + ".xml";
		response.setHeader("Content-Disposition", "attachment;filename=" + file);

		networkMarshaller.marshall(network, response.getOutputStream());
	}

	@RequestMapping(value = "/network", method = RequestMethod.POST)
	public void importFromXml(@RequestParam("networkXml") MultipartFile networkXml, HttpServletResponse resp)
			throws Exception {

		JSONObject jsonResponse = new JSONObject();

		if (!networkXml.isEmpty()) {
			DistributionNetwork network = networkParser.parse(networkXml.getInputStream());
			networkManager.save(network);
			jsonResponse.put("success", true);
		} else {
			jsonResponse.put("success", false);
		}

		// FIXME This text/html content type is probably a bug in ExtJS 4
		resp.setContentType("text/html");
		resp.getWriter().println(jsonResponse.toString(2));
	}

	private JSONArray nodesToJson(final Collection<Facility> nodes) {
		JSONArray nodesArray = new JSONArray();
		for (Facility n : nodes) {
			JSONObject nodeObject = new JSONObject();
			nodeObject.element("id", "n_" + n.getId());
			nodeObject.element("text", n.getName());
			nodeObject.element("leaf", true);
			nodesArray.add(nodeObject);
		}
		return nodesArray;
	}

	private JSONArray edgesToJson(final Collection<DistributionChannel> edges) {
		JSONArray edgesArray = new JSONArray();
		for (DistributionChannel e : edges) {
			JSONObject edgeObject = new JSONObject();
			edgeObject.element("id", "e_" + e.getId());
			final String caption = e.getSource().getName() + " > " + e.getTarget().getName();
			edgeObject.element("text", caption);
			edgeObject.element("leaf", true);
			edgesArray.add(edgeObject);
		}
		return edgesArray;
	}

}
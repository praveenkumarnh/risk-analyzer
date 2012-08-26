package com.scirisk.riskanalyzer.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;
import com.scirisk.riskanalyzer.web.bean.NetworkEdgeFormBean;

public class EdgeControllerTest {

	EdgeController controller;

	@Before
	public void beforeTest() {
		this.controller = new EdgeController();
		this.controller.networkEdgeManager = mock(NetworkEdgeManager.class);
		this.controller.networkNodeManager = mock(NetworkNodeManager.class);
	}

	@Test
	public void testSave() throws Exception {
		NetworkEdgeFormBean edge = new NetworkEdgeFormBean();
		edge.setId(new Long(13));
		edge.setPurchasingVolume(0.5);
		edge.setSourceId(new Long(113));
		edge.setTargetId(new Long(311));

		ResponseEntity<String> responseEntity = controller.save(edge);
		verify(controller.networkEdgeManager).save(edge.getId(),
				edge.getPurchasingVolume(), edge.getSourceId(),
				edge.getTargetId());
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testRead() throws Exception {
		Long edgeId = new Long(13);
		NetworkEdge stub = new NetworkEdge();
		stub.setId(edgeId);
		stub.setPurchasingVolume(0.5);
		NetworkNode source = new NetworkNode();
		source.setId(new Long(113));
		NetworkNode target = new NetworkNode();
		target.setId(new Long(311));
		stub.setSource(source);
		stub.setTarget(target);

		List<NetworkNode> stubList = new ArrayList<NetworkNode>();
		when(controller.networkEdgeManager.findOne(edgeId)).thenReturn(stub);
		when(controller.networkNodeManager.findAll()).thenReturn(stubList);

		NetworkEdgeFormBean edge = controller.read(edgeId);
		Assert.assertEquals(edgeId, edge.getId());
		Assert.assertEquals(new Double(0.5), edge.getPurchasingVolume());
		Assert.assertEquals(new Long(113), edge.getSourceId());
		Assert.assertEquals(new Long(311), edge.getTargetId());
		Assert.assertEquals(edge.getNodes(), stubList);
	}

	// NetworkEdge edge = networkEdgeManager.findOne(edgeId);
	// List<NetworkNode> nodes = networkNodeManager.findAll();
	// return new NetworkEdgeFormBean(edge, nodes);

	@Test
	public void testDelete() throws Exception {
		Long nodeId = new Long(13);
		ResponseEntity<String> responseEntity = controller.delete(nodeId);
		verify(controller.networkEdgeManager).delete(nodeId);
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

}

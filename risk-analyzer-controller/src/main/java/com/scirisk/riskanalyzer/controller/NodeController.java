package com.scirisk.riskanalyzer.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

@Controller
@RequestMapping(value = "/node")
public class NodeController {

	@Autowired
	NetworkNodeManager networkNodeManager;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> save(NetworkNode node) throws Exception {
		networkNodeManager.save(node);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	NetworkNode read(@PathVariable("id") String nodeId) throws Exception {
		return networkNodeManager.findOne(nodeId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") String nodeId)
			throws Exception {
		networkNodeManager.delete(nodeId);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Collection<NetworkNode> findAll() throws Exception {
		return networkNodeManager.findAll();
	}

}
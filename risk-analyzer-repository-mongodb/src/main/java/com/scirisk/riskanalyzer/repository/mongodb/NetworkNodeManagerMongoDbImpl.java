package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.List;

import com.mongodb.DB;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkNodeManagerMongoDbImpl implements NetworkNodeManager {

	private DB db;

	public NetworkNodeManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public Long save(NetworkNode node) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public NetworkNode findOne(Long nodeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public void delete(Long nodeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public List<NetworkNode> findAll() {
		throw new IllegalStateException("I'm not implemented yet");
	}

}

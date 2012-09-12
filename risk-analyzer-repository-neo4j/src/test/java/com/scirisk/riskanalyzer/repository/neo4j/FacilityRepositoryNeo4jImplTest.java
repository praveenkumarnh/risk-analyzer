package com.scirisk.riskanalyzer.repository.neo4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

public class FacilityRepositoryNeo4jImplTest {

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	GraphDatabaseService databaseService;

	@Before
	public void beforeTest() throws Exception {
		databaseService = new GraphDatabaseFactory()
				.newEmbeddedDatabase(tmpFolder.getRoot().getAbsolutePath());
	}

	@Test
	public void testMe() throws Exception {
		Transaction transaction = databaseService.beginTx();
		Index<Node> facilityIndex = databaseService.index()
				.forNodes("Facility");
		try {
			Node facilityNode1 = databaseService.createNode();
			facilityNode1.setProperty("name", "Antibes");
			facilityIndex.add(facilityNode1, "name", "Antibes");

			Node facilityNode2 = databaseService.createNode();
			facilityNode2.setProperty("name", "Sophia-Antipolis");
			facilityIndex.add(facilityNode2, "name", "Sophia-Antipolis");

			Relationship relationship = facilityNode1.createRelationshipTo(
					facilityNode2, RelType.DISTRIBUTES_TO);
			relationship.setProperty("purchasingVolume", 0.74);

			transaction.success();
			
			Node foundFacility = facilityIndex.get("name", "Antibes").getSingle();
			System.out.println(foundFacility.getProperty("name"));
		} finally {
			transaction.finish();
		}
	}

	@After
	public void afterTest() throws Exception {
		databaseService.shutdown();
	}

	enum RelType implements RelationshipType {
		DISTRIBUTES_TO
	}

}
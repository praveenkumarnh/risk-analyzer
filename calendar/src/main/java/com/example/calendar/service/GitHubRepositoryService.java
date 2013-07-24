package com.example.calendar.service;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.stereotype.Component;

@Component
public class GitHubRepositoryService implements RepositoryService {

	@Override
	public List<Repository> findByUser(UserContext context) throws IOException {
		GitHubClient client = new GitHubClient();
		client.setCredentials(context.getCurrentUser().getEmail(), context
				.getCurrentUser().getPassword());
		org.eclipse.egit.github.core.service.RepositoryService rs = new org.eclipse.egit.github.core.service.RepositoryService(
				client);
		return rs.getRepositories();
	}

	@Override
	public Repository findByUser(UserContext context, String repoName)
			throws IOException {
		GitHubClient client = new GitHubClient();
		client.setCredentials(context.getCurrentUser().getEmail(), context
				.getCurrentUser().getPassword());

		org.eclipse.egit.github.core.service.RepositoryService rs = new org.eclipse.egit.github.core.service.RepositoryService(client);
		return rs.getRepository(context.getCurrentUser().getName(), repoName);
	}

	public Repository create(UserContext context, String name,
			String description) throws IOException {
		GitHubClient client = new GitHubClient();
		client.setCredentials(context.getCurrentUser().getEmail(), context
				.getCurrentUser().getPassword());

		org.eclipse.egit.github.core.service.RepositoryService rs = new org.eclipse.egit.github.core.service.RepositoryService(
				client);
		Repository r = new Repository();
		r.setName(name);
		r.setPrivate(false);
		r.setDescription(description);
		return rs.createRepository(r);
	}

}

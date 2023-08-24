package com.githubhelper.RepositoryFetcher.repositoryfetcher.connector;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;

import java.util.List;

public interface ApiConnector {

    String getJsonFromAPI(String url);
}

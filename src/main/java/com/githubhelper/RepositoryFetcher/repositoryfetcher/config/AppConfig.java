package com.githubhelper.RepositoryFetcher.repositoryfetcher.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:githubApiEndpoints.properties")
public class AppConfig {}

package com.githubhelper.RepositoryFetcher.repositoryfetcher.controller;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.RepoBranchInfo;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubHelperController {

    private final GithubService githubService;

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<RepoBranchInfo> getRepoBranchInfo(@PathVariable String username) {
        return githubService.getAllRepositoriesWithBranches(username);
    }
}

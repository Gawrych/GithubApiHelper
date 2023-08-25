# Github API Helper
### The Github API Helper provides you repositories and their branches from a user
## Request
  * List all GitHub repositories, including their branches, from the user `GET/api/v1/repositories/{username}`

  ### Response example

  ```
[
    {
        "repositoryName": {name},
        "ownerLogin": {login},
        "branches": [
            {
                "name": {name},
                "sha": {sha}
            }
        ]
    }
]
  ```

## Exception example

```
{
    "status": {responseCode},
    "message": {reason}
}
```

## Unit tests
![UnitTests](https://github.com/Gawrych/GithubApiHelper/assets/UnitTests.png)


## Tech Stack
* Java 17
* Spring Boot 3.1.2

## Contact
Paweł Gawrych - [LinkedIn](www.linkedin.com/in/Gawrych) <br/>
Email - pawelgawrych203@gmail.com <br/>
Web Application - [DelayFlight.pl](https://www.delayflight.pl/) <br/>

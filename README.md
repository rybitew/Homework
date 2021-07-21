# Homework
Recruitment task - simple application detecting the gender based on the name.
Docker image is available on https://hub.docker.com/r/rybitew/homework

## Endpoints
Endpoints are provided on port 8080:
* /tokens/{male/female} - All available tokens for respectively male and female names.
* /identify/{first/full}/{name} - Returns the gender based on the name and the selected option:
    * name - name for which gender is identified.
    * first - identification based only on the first name.
    * full - identification based on the full name.

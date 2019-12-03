# PPKWU-Zad4
### Spring Boot application converting personal information Lodz University of Technology employees into vCard

Endpoints
=============

##### In general, application provides two endpoints (both of them is not case sensitive):
- /search?name="name, surname oraz both"

This endpoint will return .html page contains list of employees, where we can choose, which of them will return vCard.

### Example
`/search?name=piotr // only name`
`/search?name=nowak // only surname`
`/search?name=piotr+nowak // both of them connected with '+' character`

- /generate?name="name, surname oraz both"

This endpoint will return vCard (.vcf) file ready to download.
If we want manually type name and surname (both are required), we must be sure that, only one person has typed name and surname.

### Example
`/generate?name=piotr+nowak`

##### If we don't want use endpoints maunally (go through the following list of steps):
- run application (application runing on port 80)
- move to browser and type "localhost"
- type name, surname or both
- wait a moment for results
- click interesting employee link
- wait a moment for your vCard file

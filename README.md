# Swimming Contests Manager Server

Server in the Swimming Contests Manager project to pass the `Programming III` subject.

This is a simple example of a REST API application for managing a database that stores data about contests, competitions, clubs, trainers, competitors and records.

The project was created using **Java** with the **Spring Boot** framework. The API is available as JSON files.  

For example, for method `GET` at `/api/trainer/club/34` we will get JSON array:
```json
[
    {
        "licenceNr": 562,
        "name": "Adrian",
        "surname": "Rybnicki",
        "clubId": 4
    },
    {
        "licenceNr": 563,
        "name": "Sandra",
        "surname": "Popowicz",
        "clubId": 4
    },
    {
        "licenceNr": 564,
        "name": "Mariusz",
        "surname": "Gotowski",
        "clubId": 4
    },
    {
        "licenceNr": 565,
        "name": "Bartłomiej",
        "surname": "Poręba",
        "clubId": 4
    }
]
```

## Full API description

### `GET` method:

- `/api/contest`
- `/api/competition`
- `/api/competition/{id}`
- `/api/club`
- `/api/club/{id}`
- `/api/competitior`
- `/api/competitior/{id}`
- `/api/competitior/club/{clubId}`
- `/api/trainer`
- `/api/trainer/{id}`
- `/api/trainer/club/{clubId}`
- `/api/record`
- `/api/record/{id}`
- `/api/record/competitor/{competitorId}`
- `api/record/competition/{competitionId}`

### `POST` method:
- `/api/contest`
- `/api/competition`
- `/api/club`
- `/api/competitor`
- `/api/trainer`
- `/api/record`

### `PUT` method:
- `/api/contest`
- `/api/club`
- `/api/competitor`
- `/api/trainer`

### `DELETE` method:
- `/api/contest/{id}`
- `/api/competition/{id}`
- `/api/club/{id}`
- `/api/competitor/{id}`
- `/api/competitor/club/{clubId}`
- `/api/trainer/{id}`
- `/api/record/{id}`
## GraphQL

### GraphQL Client - Altair

- https://altairgraphql.dev/

#### GraphQL test

- StarWars API - https://swapi-graphql.eskerda.vercel.app/

`query`

```graphql
{
  allFilms {
    films {
      title
    }
  }
}
```

`response`

```json
{
  "data": {
    "allFilms": {
      "films": [
        {
          "title": "A New Hope"
        },
        {
          "title": "The Empire Strikes Back"
        },
        {
          "title": "Return of the Jedi"
        },
        {
          "title": "The Phantom Menace"
        },
        {
          "title": "Attack of the Clones"
        },
        {
          "title": "Revenge of the Sith"
        },
        {
          "title": "The Force Awakens"
        }
      ]
    }
  }
}
```

### GraphQL Voyager - 스키마 시각화

- https://graphql-kit.com/graphql-voyager/

### 기본 문법

#### Operation Name

```graphql
query getAllPlanets {
  allPlanets {
    planets {
      name
      diameter
      rotationPeriod
      id
    }
  }
}
```

#### Arguments

```graphql
query getPlanet {
  planet(id: "cGxhbmV0czox") {
    id
    name
    gravity
  }
}
```

#### Variables

```graphql
query getPlanetWithVariable($id: ID! = "default value") {
  planet(id: $id) {
    id
    name
    gravity
  }
}

# Variables
{
  "id": "cGxhbmV0czox"
}
```

#### Alias

```graphql
query getPlanetWithVariable($id: ID!) {
  myPlanet: planet(id: $id) {
    id
    name
    gravity
  }

  yourPlanet: planet(id: $id) {
    id
    name
    gravity
  }
}
```

#### Fragment

```graphql
query getPlanetWithFragment {
  planet(id: "cGxhbmV0czox") {
    ...planetField
    created
  }
}

fragment planetField on Planet {
  id
  name
  gravity
  filmConnection(first: 1) {
    films {
      id
      title
    }
  }
}
```

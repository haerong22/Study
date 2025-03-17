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

#### Directive

```graphql
query planet($id: ID! = "cGxhbmV0czox", $isAnonymus: Boolean!) {
  privatePlanet: planet(id: $id) @skip(if: $isAnonymus) {
    id
    name
    diameter
    rotationPeriod
    gravity
    filmConnection {
      films {
        title
      }
    }
  }

  publicPlanet: planet(id: $id) {
    id
    name
    diameter
    rotationPeriod
    gravity
    filmConnection {
      films {
        title
      }
    }
  }
}
```

```graphql
query planet($id: ID! = "cGxhbmV0czox", $isMine: Boolean!) {
  privatePlanet: planet(id: $id) {
    id
    name
    diameter
    rotationPeriod
    gravity
    filmConnection @include(if: $isMine) {
      films {
        title
      }
    }
  }

  publicPlanet: planet(id: $id) {
    id
    name
    diameter
    rotationPeriod
    gravity
    filmConnection {
      films {
        title
      }
    }
  }
}
```

#### Inline Fragments

```graphql
query planet($id: ID! = "cGxhbmV0czox") {
  privatePlanet: planet(id: $id) {
    id
    ... on Planet {
      name
      diameter
      rotationPeriod
      gravity
    }
    filmConnection {
      films {
        title
      }
    }
  }
}
```

- type 이 planet 일 경우와 person 일 경우 다른 형태로 응답

```graphql
query node {
  node(id: "cGxhbmV0czox") {
    ... on Planet {
      id
      name
      gravity
    }

    ... on Person {
      id
      name
      birthYear
    }
  }
}
```

#### \_\_typename

- typename(메타 필드) 같이 응답

```graphql
query node {
  node(id: "cGxhbmV0czox") {
    __typename
    ... on Planet {
      id
      name
      gravity
    }

    ... on Person {
      id
      name
      birthYear
    }
  }
}
```

#### Introspection

- graphql 서버의 스키마에 대한 정보를 쿼리할 수 있게 해주는 기능
- 이 기능을 통해 클라이언트 도구에서 문서화와 자동완성을 제공

#### Meta Field

- graphql 의 스키마에 대한 정보를 제공
- 별도의 작성 없이 자동으로 생성되는 필드
- \_\_schema, \_\_type, \_\_typename

```graphql
{
  __schema {
    types {
      kind
      name
    }

    queryType {
      name
      fields {
        name
      }
    }

    subscriptionType {
      name
    }

    directives {
      name
    }
  }
}
```

```graphql
{
  __type(name: "Query") {
    name
    fields {
      name
      type {
        name
      }
      args {
        name
      }
    }
  }
}
```

```graphql
{
  __typename
  allPlanets {
    planets {
      __typename
    }
  }
}
```

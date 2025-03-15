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

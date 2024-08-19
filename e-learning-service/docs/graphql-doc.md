### GraphQL 문서화 도구

#### Voyager
- https://github.com/graphql-kit/graphql-voyager/tree/main/example

`src/main/resources/static/voyager.html`
```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>GraphQL Voyager</title>
    <link rel="stylesheet" href="https://unpkg.com/graphql-voyager/dist/voyager.css" />
    <script src="https://unpkg.com/graphql-voyager/dist/voyager.min.js"></script>
</head>

<body>
<script>
    GraphQLVoyager.init(document.body, {
        introspection: {
            url: '/graphql',
        },
    })
</script>
</body>

</html> 
```

#### Playground 
- https://github.com/graphql/graphql-playground/tree/main/packages/graphql-playground-html

`src/main/resources/static/playground.html`
```html
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8" />
  <title>GraphQL Playground</title>
  <link rel="stylesheet" href="https://unpkg.com/graphql-playground-react/build/static/css/index.css" />
  <script src="https://unpkg.com/graphql-playground-react/build/static/js/middleware.js"></script>
</head>

<body>
  <script>
    window.addEventListener('load', function(event) {
      GraphQLPlayground.init(document.body, {
        endpoint: '/graphql',
      })
    })
  </script>
</body>

</html>
```


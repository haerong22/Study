### Vue3 with apollo

#### 설치
- vue cli 설치
```shell
npm install -g @vue/cli
```

- vue 프로젝트 생성
```shell
vue create {project name}

# 실행 
npm run serve
```

- apollo client 설정
```shell
# 관련 패키지 설치
npm install @vue/apollo-option @apollo/client graphql graphql-tag axios
```

`src/apollo.js`
```js
import { ApolloClient, InMemoryCache, createHttpLink } from '@apollo/client/core';
import { setContext } from '@apollo/client/link/context';
import { createApolloProvider } from '@vue/apollo-option';

const httpLink = createHttpLink({
    uri: 'http://localhost:9000/graphql',
});

const authLink = setContext((_, { headers }) => {
    const token = localStorage.getItem('jwt');
    return {
        headers: {
            ...headers,
            authorization: token ? `Bearer ${token}` : '',
        },
    };
});

const apolloClient = new ApolloClient({
    link: authLink.concat(httpLink),
    cache: new InMemoryCache(),
});

export const apolloProvider = createApolloProvider({
    defaultClient: apolloClient,
});

export default apolloClient;
```

`src/main.js`
```js
import { createApp, h } from 'vue';
import App from './App.vue';
import { apolloProvider } from './apollo';
import router from './router';

const app = createApp({
    render: () => h(App),
});

app.use(apolloProvider);
app.use(router);
app.mount('#app');
```
import { createApp, h } from 'vue';
import App from './App.vue';
import { apolloProvider } from './apollo';

const app = createApp({
    render: () => h(App),
});

app.use(apolloProvider);
app.mount('#app');
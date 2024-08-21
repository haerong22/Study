<template>
  <v-app>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

import {provideApolloClient} from "@vue/apollo-composable";
import apolloClient from "@/apollo";

provideApolloClient(apolloClient);

export default {
  name: 'App',

  data: () => ({
    //
  }),
}
</script>

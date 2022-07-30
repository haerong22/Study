<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";

const title = ref("");
const content = ref("");


const write = () => {
  console.log("hi")
  axios.get("http://localhost:8080");
}

</script>

<template>

  <div>
    <div>
      <el-input v-model="title" placeholder="제목을 입력해주세요." />
    </div>

    <div class="mt-2">
      <el-input v-model="content" type="textarea" rows="15" />
    </div>

    <div class="mt-2">
      <el-button type="primary" @click="write()">글 작성완료</el-button>
    </div>
  </div>

</template>

<style>

</style>
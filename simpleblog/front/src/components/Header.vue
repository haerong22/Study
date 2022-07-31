<script setup lang="ts">

</script>

<template>
  <el-header class="header">
    <el-menu mode="horizontal" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write">글 작성</el-menu-item>
    </el-menu>
    <!--    <RouterLink to="/">Home</RouterLink>-->
    <!--    <RouterLink to="/write">글 작성</RouterLink>-->
  </el-header>
</template>

<style scoped>

.header {
  padding: 0;
  height: 60px;
}

</style>
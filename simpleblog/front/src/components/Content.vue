<script setup lang="ts">

</script>

<template>
  <el-main class="content">
    <el-row>
      <el-col>
        <RouterView />
      </el-col>
    </el-row>
  </el-main>
</template>

<style scoped>

.content {
  height: calc(100vh - 60px);
}

</style>
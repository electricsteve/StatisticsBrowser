<script setup lang="ts">
import AppInfo from "./AppInfo.vue";
import PlayerList from "./PlayerList.vue";
</script>

<template>
  <div class="left-sidebar">
    <AppInfo />
    <PlayerList />
  </div>
</template>

<style scoped>
.left-sidebar {
  min-height: 100svh;
  display: flex;
  flex-direction: column;
}
</style>
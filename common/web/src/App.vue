<script setup lang="ts">
import LeftSidebar from "./components/LeftSidebar.vue";
import IndividualStatistics from "./components/IndividualStatistics.vue";
import RightSidebar from "./components/RightSidebar.vue";
</script>

<template>
  <div class="app-layout">
    <aside class="sidebar sidebar-left">
      <LeftSidebar />
    </aside>
    <main class="app-layout__center">
      <IndividualStatistics />
    </main>
    <aside class="sidebar sidebar-right">
      <RightSidebar />
    </aside>
  </div>
</template>

<style scoped>
.app-layout {
  min-height: 100svh;
  display: grid;
  grid-template-columns: minmax(320px, 1fr) minmax(640px, 2fr) minmax(320px, 1fr);
  background: var(--bg);
}

.sidebar {
  min-width: 0;
}

.app-layout__center {
  min-width: 0;
  width: 100%;
  justify-self: center;
  border-inline: 4px solid var(--border);
}
</style>

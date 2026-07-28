<script setup lang="ts">
</script>

<template>
  <div class="right-sidebar">
    <div class="right-sidebar__fetch">
      <span>Last fetch: 15s ago</span>
      <button type="button">Refresh</button>
    </div>
  </div>
</template>

<style scoped>
.right-sidebar {
  min-height: 100svh;
  padding: 16px 18px;
  display: flex;
  flex-direction: column;
  color: var(--text-h);
}

.right-sidebar__fetch {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-top: 10px;
  color: var(--text-h);
}

button {
  border: 4px solid var(--border);
  border-radius: 999px;
  padding: 8px 28px;
  background: transparent;
  color: var(--text-h);
  cursor: pointer;
}
</style>
<script setup lang="ts">
const statistics = [
  {name: 'Distance walked', value: '1.2 km'},
  {name: 'Blocks mined', value: '4,382'},
  {name: 'Mobs defeated', value: '128'},
  {name: 'Items crafted', value: '76'},
  {name: 'Villagers traded with', value: '20'},
  {name: 'Days played', value: '43'},
]
</script>

<template>
<div class="stats-panel__header">
  <span>Statistic</span>
  <span>Value</span>
</div>

<div class="stats-list" role="list">
  <div v-for="stat in statistics" :key="stat.name" class="stats-row" role="listitem">
    <span class="stats-name">{{ stat.name }}</span>
    <span class="stats-value">{{ stat.value }}</span>
  </div>
</div>
</template>

<style scoped>
.stats-panel__header {
  display: grid;
  grid-template-columns: 1fr auto;
  padding: 10px 0 6px;
  border-bottom: 4px solid var(--border);
  color: var(--text-h);
}

.stats-list {
  display: grid;
  gap: 0;
  min-height: 0;
  width: 100%;
}

.stats-row {
  display: grid;
  grid-template-columns: 1fr auto;
  gap: 12px;
  align-items: center;
  padding: 12px 0 10px;
  border-bottom: 4px solid rgba(255, 255, 255, 0.45);
}

.stats-name {
  color: var(--text-h);
}

.stats-value {
  color: var(--text-h);
  font-variant-numeric: tabular-nums;
}
</style>
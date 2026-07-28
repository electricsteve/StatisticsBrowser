<script setup lang="ts">
import {ref} from 'vue'

const player = ref('ElectricSteve')
const uuid = 'UUID: b8854d33-d71b-4c32-b00f-54edc7d9f5e'
const tabs = ['Custom', 'Items', 'Mobs']
const activeTab = ref('Custom')
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
  <main class="page-shell">
    <header class="topbar">
      <div class="topbar-copy">
        <div class="player-mark" aria-hidden="true"></div>
        <h1>{{ player }}</h1>
        <div class="player-uuid">{{ uuid }}</div>
      </div>

      <nav class="tab-row" aria-label="Statistic categories">
        <button
          v-for="tab in tabs"
          :key="tab"
          class="tab-button"
          :class="{ active: tab === activeTab }"
          type="button"
          @click="activeTab = tab"
        >
          {{ tab }}
        </button>
      </nav>
    </header>

    <section class="stats-panel" aria-label="Statistics overview">
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
    </section>
  </main>
</template>

<style scoped>
.page-shell {
  width: 100%;
  min-height: 100svh;
  padding: 0;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  color: var(--text-h);
}

.topbar {
  display: flex;
  flex-direction: column;
  align-items: stretch;
}

.topbar-copy {
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  gap: 10px;
  padding: 4px 16px 0 14px;
  min-height: 60px;
}

h1 {
  font-size: 1.9rem;
  font-weight: 400;
  line-height: 1;
  letter-spacing: 0;
  color: var(--text-h);
  margin: 0;
  font-family: var(--heading), sans-serif;
}

.player-mark {
  width: 38px;
  height: 38px;
  border: 3px solid var(--border);
  border-radius: 4px;
  background:
    linear-gradient(135deg, rgba(255, 255, 255, 0.14), transparent),
    #ad8c52;
  image-rendering: pixelated;
}

.player-uuid {
  justify-self: end;
  color: var(--text);
  font-size: 0.9rem;
}

.tab-row {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  border-top: 4px solid var(--border);
  border-bottom: 4px solid var(--border);
}

.tab-button {
  appearance: none;
  border: 0;
  border-right: 4px solid var(--border);
  padding: 6px 10px;
  color: var(--text-h);
  background: transparent;
  cursor: pointer;
  font-size: 1rem;
}

.tab-button:last-child {
  border-right: 0;
}

.tab-button.active {
  color: var(--text-h);
}

.stats-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
  padding: 0 16px 0 14px;
}

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

<script setup lang="ts">
import {ref} from 'vue'
import type { PlayerInfo } from '../types/PlayerInfo'
import CustomTab from "./CustomTab.vue";
import ItemsTab from "./ItemsTab.vue";
import MobsTab from "./MobsTab.vue";

const props = defineProps<{
  player: PlayerInfo
}>()
const tabs = ['Custom', 'Items', 'Mobs']
const activeTab = ref('Custom')

</script>

<template>
  <main class="page-shell">
    <header class="topbar">
      <div class="topbar-copy">
        <img class="player-mark" aria-hidden="true" :src="'/api/playerhead/' + player.uuid" alt="player head"/>
        <h1>{{ player.name }}</h1>
        <div class="player-uuid">UUID: {{ player.uuid }}</div>
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
      <CustomTab v-if="activeTab === 'Custom'"/>
      <ItemsTab v-else-if="activeTab === 'Items'"/>
      <MobsTab v-else-if="activeTab === 'Mobs'"/>
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
  background-color: rgba(255, 255, 255, 0.1);
}

.stats-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
  padding: 0 16px 0 14px;
}

</style>

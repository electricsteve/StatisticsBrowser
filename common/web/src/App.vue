<script setup lang="ts">
import LeftSidebar from "./components/LeftSidebar.vue";
import IndividualStatistics from "./components/IndividualStatistics.vue";
import RightSidebar from "./components/RightSidebar.vue";
import type {PlayerInfo} from "./types/PlayerInfo.ts";
import {onMounted, type Ref, ref} from "vue";

const players: Ref<PlayerInfo[]> = ref([])
const selectedPlayer: Ref<PlayerInfo | null> = ref(null)
const loading = ref(true)
const error = ref("")

async function fetchPlayers() {
  loading.value = true
  try {
    const res = await fetch('/api/playerlist?offline=true')
    if (!res.ok) throw new Error('Failed to fetch players')
    players.value = await res.json()
  } catch (e) {
    if (e instanceof Error) {
      error.value = e.message
    }
  } finally {
    loading.value = false
  }
}

onMounted(fetchPlayers)

function handleSelect(player : PlayerInfo) {
  selectedPlayer.value = player
}
</script>

<template>
  <div class="app-layout">
    <aside class="sidebar sidebar-left">
      <LeftSidebar
        :players="players"
        :selected-player-uuid="selectedPlayer?.uuid ?? null"
        :loading="loading"
        :error="error"
        @select_player="handleSelect"
      />
    </aside>
    <main class="app-layout__center">
      <IndividualStatistics v-if="selectedPlayer" :player="selectedPlayer" />
      <div v-else>
        <p>Please select a player to view their statistics.</p>
      </div>
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

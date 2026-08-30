<script setup lang="ts">
import type {PlayerInfo} from "../types/PlayerInfo.ts";

const props = defineProps<{
  players: PlayerInfo[];
  selectedPlayerUuid: string | null;
  loading: boolean;
  error: string;
}>()
const emit = defineEmits<{
  select_player: [player: PlayerInfo]
}>()
</script>

<template>
  <div class="player-list" role="list">
    <button v-for="player in players" :key="player.name" class="player-row" role="menuitem" @click="emit('select_player', player)">
      <img class="player-mark" :src="'/api/playerhead/' + player.uuid" alt="player head" aria-hidden="true">
      <span class="player-name">{{ player.name }}</span>
    </button>
  </div>
</template>

<style scoped>
.player-list {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-top: 36px;
  color: var(--text-h);
}

.player-row {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 16px;
  width: 100%;
  padding: 14px 0;
}

.player-name {
  color: var(--text-h);
  font-size: clamp(1.55rem, 2.2vw, 2rem);
  line-height: 1.1;
}

.player-mark {
  width: clamp(44px, 5vw, 56px);
  height: clamp(44px, 5vw, 56px);
  border: 2px solid var(--border);
  border-radius: 4px;
  image-rendering: pixelated;
}
</style>
<script setup lang="ts">
import AppInfo from "./AppInfo.vue";
import PlayerList from "./PlayerList.vue";
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
  <div class="left-sidebar">
    <AppInfo />
    <PlayerList
      :players="props.players"
      :selected-player-uuid="props.selectedPlayerUuid"
      :loading="props.loading"
      :error="props.error"
      @select_player="emit('select_player', $event)"
    />
  </div>
</template>

<style scoped>
.left-sidebar {
  min-height: 100svh;
  display: flex;
  flex-direction: column;
}
</style>
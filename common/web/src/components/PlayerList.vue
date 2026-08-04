<script setup lang="ts">
const players = [
  {name: 'ElectricSteve', uuid: 'b8854d33-d71b-4c32-b00f-54edc7d9f5e'},
]
</script>

<template>
  <div class="player-list" role="list">
    <button v-for="player in players" :key="player.name" class="player-row" role="menuitem">
      <div class="player-mark" aria-hidden="true"></div>
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
  background:
      linear-gradient(135deg, rgba(255, 255, 255, 0.14), transparent),
      #ad8c52;
  image-rendering: pixelated;
}
</style>
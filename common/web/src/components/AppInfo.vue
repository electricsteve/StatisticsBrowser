<script setup lang="ts">
import logo from '../assets/vue.svg'
</script>

<template>
  <div class="app-info">
    <div class="app-info__brand">
      <img class="app-info__logo" :src="logo" alt="" aria-hidden="true" />
      <div class="app-info__titles">
        <div class="app-info__title-row">
          <span class="app-info__name">Statistics Browser</span>
          <span class="app-info__version">v0.1.2</span>
        </div>
        <div class="app-info__description-row">
          <span class="app-info__description">A webapp to view Minecraft player's statistics.</span>
          <span class="app-info__minecraft">Minecraft 26.1.2</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.app-info {
  padding: 16px 18px 12px;
  border-bottom: 4px solid var(--border);
}

.app-info__brand {
  display: grid;
  grid-template-columns: auto 1fr;
  align-items: center;
  gap: 16px;
}

.app-info__logo {
  width: 78px;
  height: 78px;
  object-fit: contain;
}

.app-info__titles {
  display: flex;
  flex-direction: column;
  gap: 6px;
  min-width: 0;
}

.app-info__title-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  min-width: 0;
}

.app-info__name {
  color: var(--text-h);
  font-size: 1.35rem;
  line-height: 1.1;
}

.app-info__version,
.app-info__description,
.app-info__minecraft {
  color: var(--text);
}

.app-info__description-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  min-width: 0;
}

.app-info__description {
  min-width: 0;
}

.app-info__minecraft {
  text-align: right;
}
</style>
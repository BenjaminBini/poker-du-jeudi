<template>
  <tailwind-slide-over
    title="Ajouter un joueur"
    :is-open="isOpen"
    @close="$emit('close')"
  >
    <ul>
      <li v-for="player in playerList" :key="player.id">
        {{ player.firstName }}
      </li>
    </ul>
  </tailwind-slide-over>
</template>
<script>
import TailwindSlideOver from "@/components/ui/TailwindSlideOver";
export default {
  name: "AddPlayerPanel",
  components: {
    TailwindSlideOver,
  },
  props: ["isOpen", "playerList"],
};
</script>
<template>
  <tailwind-slide-over
    title="Ajouter un joueur"
    :is-open="isOpen"
    @close="$emit('close')"
  >
    <ul class="divide-y divide-gray-200">
      <li class="flex" v-for="player in playerList" :key="player.playerId">
        <a
          @click="$emit('player-clicked', player)"
          class="flex items-center flex-1 px-4 py-4 space-x-2 font-medium text-indigo-600 truncate cursor-pointer align-center hover:bg-gray-50"
        >
          <span>{{ player.firstName }}</span>
          <span class="text-sm text-gray-500">
            {{ player.playerResults.length }} session{{
              player.playerResults.length > 1 ? "s" : ""
            }}
          </span>
        </a>
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
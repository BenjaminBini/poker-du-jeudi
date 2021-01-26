<template>
  <div
    class="flex items-center justify-between px-3 py-2 border rounded-md cursor-pointer hover:shadow-md group hover:text-white"
    @click="togglePlayer"
    :class="
      selected
        ? 'bg-green-400 border-green-400 text-white shadow-md'
        : 'text-gray-700 border-gray-300 hover:bg-indigo-400 hover:border-indigo-400 shadow-sm'
    "
  >
    <div>{{ player.firstName }}</div>
    <div
      class="text-sm text-gray-500 group-hover:text-white"
      :class="selected ? 'hidden' : ''"
    >
      {{ player.playerResults.length }}
      <span class="hidden lg:inline"
        >session{{ player.playerResults.length > 1 ? "s" : "" }}</span
      >
    </div>
    <div
      v-if="selected"
      class="flex items-center justify-center w-6 h-6 bg-red-400 rounded-full md:opacity-0 group-hover:opacity-100"
    >
      <x-icon class="w-4 h-4"></x-icon>
    </div>
  </div>
</template>

<script>
import { XIcon } from "@vue-hero-icons/outline";
export default {
  components: { XIcon },
  data: function () {
    return {};
  },
  props: {
    player: {
      type: Object,
      default: () => {},
    },
    selected: {
      type: Boolean,
      default: () => false,
    },
  },
  methods: {
    togglePlayer: function () {
      this.selected
        ? this.$emit("player-remove", this.player)
        : this.$emit("player-select", this.player);
    },
  },
};
</script>

<style>
</style>
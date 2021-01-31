<template>
  <stat-card
    :to="
      loading ? '' : `/players/${results[results.length - 1].player.playerId}`
    "
    label="Perdant"
    :loading="loading"
    :stat="!loading ? results[results.length - 1].player.firstName : ''"
    :change="!loading ? results[results.length - 1] : ''"
    color-class="bg-red-500"
  >
    <chevron-double-down-icon
      class="w-6 h-6 text-white"
    ></chevron-double-down-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { ChevronDoubleDownIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    ChevronDoubleDownIcon,
    StatCard,
  },
  props: {
    results: Array,
  },
  computed: {
    loading: function () {
      return !this.results || this.results.length == 0;
    },
  },
};
</script>

<style>
</style>
<template>
  <stat-card
    label="Dernières places"
    :loading="loading"
    :stat="`${lastPlaces} (${Math.ceil((lastPlaces / results.length) * 100)}%)`"
    color-class="bg-red-500"
    :neutral-badge="year"
  >
    <chevron-double-down-icon
      class="w-6 h-6 text-white"
    ></chevron-double-down-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { ChevronDoubleDownIcon } from "@vue-hero-icons/solid";
export default {
  components: {
    ChevronDoubleDownIcon,
    StatCard,
  },
  props: {
    results: Array,
    year: Number,
  },
  computed: {
    loading: function () {
      return !this.results || this.results.length == 0;
    },
    lastPlaces: function () {
      if (this.loading) return 0;
      return this.results.filter((pr) => pr.last).length;
    },
  },
};
</script>

<style>
</style>
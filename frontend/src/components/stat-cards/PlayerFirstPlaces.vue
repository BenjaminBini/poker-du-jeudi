<template>
  <stat-card
    label="Premières places"
    :loading="loading"
    :stat="`${firstPlaces} (${Math.ceil(
      (firstPlaces / results.length) * 100
    )}%)`"
    color-class="bg-green-500"
    :neutral-badge="year"
  >
    <academic-cap-icon class="w-6 h-6 text-white"></academic-cap-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { AcademicCapIcon } from "@vue-hero-icons/solid";
export default {
  components: {
    AcademicCapIcon,
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
    firstPlaces: function () {
      if (this.loading) return 0;
      return this.results.filter((pr) => pr.rank === 1).length;
    },
  },
};
</script>

<style>
</style>
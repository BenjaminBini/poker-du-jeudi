<template>
  <stat-card
    :to="loading ? '' : `/players/${results[0].player.playerId}`"
    label="Vainqueur"
    :loading="loading"
    :stat="!loading ? results[0].player.firstName : ''"
    :change="!loading ? results[0] : ''"
    color-class="bg-green-500"
  >
    <star-icon class="w-6 h-6 text-white"></star-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { StarIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    StarIcon,
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
<template>
  <stat-card
    label="Lieu"
    :loading="loading"
    :stat="!loading ? session.place.name : ''"
    color-class="bg-green-500"
  >
    <HomeIcon class="w-6 h-6 text-white"></HomeIcon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { HomeIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    HomeIcon,
    StatCard,
  },
  props: {
    session: {
      type: Object,
      default: undefined,
    },
  },
  computed: {
    loading: function () {
      return !this.session || !this.session.sessionId;
    },
  },
};
</script>

<style>
</style>
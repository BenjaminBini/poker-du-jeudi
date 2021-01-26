<template>
  <div class="overflow-hidden bg-white shadow sm:rounded-md">
    <ul v-if="loading">
      <li>
        <player-list-item :loading="true"></player-list-item>
      </li>
    </ul>
    <ul v-if="!loading" class="divide-y divide-gray-200">
      <li v-for="player in players" :key="player.playerId">
        <player-list-item :player="player"></player-list-item>
      </li>
    </ul>
  </div>
</template>

<script>
import PlayerService from "../../../services/player-service";

export default {
  name: "poker-players",
  data: () => ({
    players: [],
    cols: [
      {
        field: "firstName",
        label: "Joueur",
      },
    ],
    loading: false,
  }),
  async mounted() {
    this.loading = true;
    const response = await PlayerService.getPlayers();
    this.players = response.data;
    this.$store.commit("setPageTitle", this.players.length + " joueurs");
    this.loading = false;
  },
  methods: {},
};
</script>

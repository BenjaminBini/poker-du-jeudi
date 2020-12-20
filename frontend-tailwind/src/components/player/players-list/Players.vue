<template>
  <div class="overflow-hidden bg-white shadow sm:rounded-md">
    <ul>
      <li
        v-for="(player, index) in players"
        :key="player.playerId"
        :class="index &gt; 0 ? 'border-t border-gray-200' : ''"
      >
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
  }),
  mounted() {
    PlayerService.getPlayers().then((response) => {
      this.players = response.data;
      this.$store.commit("setPageTitle", this.players.length + " joueurs");
    });
  },
  methods: {},
};
</script>

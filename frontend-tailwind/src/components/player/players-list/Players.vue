<template lang="pug">
  //tw-table(:items="players", :cols="cols")
  .bg-white.shadow.overflow-hidden(class="sm:rounded-md")
    ul

      li(v-for="(player, index) in players"
        :class="index > 0 ? 'border-t border-gray-200' : ''")
        player-list-item(:player="player")
</template>

<script>
import PlayerService from "../../../services/player-service";

export default {
  name: "poker-players",
  data: () => ({
    players: [],
    cols: [{
      field: 'firstName',
      label: 'Joueur',
    }]
  }),
  mounted() {
    PlayerService.getPlayers().then(response => {
      this.players = response.data;
      this.$store.commit('setPageTitle', this.players.length + ' joueurs');
    });
  },
  methods: {
  }
}
</script>

<template lang="pug">
  b-container
    b-row
      b-col.mb-4
        h1 {{players.length}} joueur{{players.length > 1 ? 's' : ''}}
    b-table(:items="players", :fields="playersFields",
      sort-by="sessionCount", sort-desc="true", striped)
      template(#cell(name)="player")
        router-link(:to="'/players/' + player.item.playerId") {{player.item.firstName}}
</template>

<script>
import PlayerService from '../services/player-service';

export default {
  name: "Players",
  data: function() {
    return {
      playersFields: [{
        key: 'name',
        label: 'Prénom',
        sortable: true,
      }, {
        key: 'sessionCount',
        label: 'Sessions',
        sortable: true,
      }, {
        key: 'totalResult',
        label: 'Résultat',
        sortable: true,
      }],
      players: []
    }
  },
  mounted() {
    PlayerService.getPlayers().then(response => {
      this.players = response.data;
      this.players.forEach(p => {
        p.sessionCount = p.playerResults.length;
        p.totalResult = p.playerResults.reduce((acc, curr) => acc + curr.result, 0);
      });
    });
  }
}
</script>

<style scoped>

</style>
<template lang="pug">
  h1 {{player.firstName}}
</template>

<script>
import PlayerService from "../../services/player-service";

export default {
  name: "Player",
  data: () => ({
    player: Object,
  }),
  mounted() {
    let playerId = this.$route.params.id;
    PlayerService.getPlayer(playerId).then(response => {
      this.player = response.data;
      this.$store.commit('setPageTitle', this.player.firstName);
    });
  }
}
</script>

<style scoped>

</style>
<template lang="pug">
  h1 {{playerId}}
</template>

<script>
export default {
  name: "Player",
  data: function() {
    return {
      playerId: null
    }
  },
  mounted() {
    this.playerId = this.$route.params.id
  }
}
</script>

<style scoped>

</style>
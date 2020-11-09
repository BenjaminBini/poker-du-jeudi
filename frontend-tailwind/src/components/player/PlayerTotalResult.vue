<template lang="pug">
  .flex.flex-col.justify-around.items-start
    .text-sm.leading-5.text-gray-900
      .mb-1.text-sm.leading-5.text-gray-500 Résultat total
      .inline-flex.items-baseline.rounded-full.text-sm.font-medium.leading-5(
        class="px-2.5 py-0.5 md:mt-2 lg:mt-0"
        :class="result >= 0 ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
      )
        svg.-ml-1.flex-shrink-0.self-center.h-5.w-5(
          class="mr-0.5" fill="currentColor" viewbox="0 0 20 20"
          :class="result >= 0 ? 'text-green-500' : 'text-red-500'")
          path(v-if="result >= 0" fill-rule="evenodd" d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z" clip-rule="evenodd")
          path(v-if="result < 0" fill-rule="evenodd" d="M5 10a1 1 0 011-1h8a1 1 0 110 2H6a1 1 0 01-1-1z" clip-rule="evenodd")
        | {{Math.abs(result)}} €
</template>

<script>
export default {
  name: "PlayerTotalResult",
  props: {
    result: Number,
  },
}
</script>

<style scoped>

</style>
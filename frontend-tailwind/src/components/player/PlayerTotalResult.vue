<template>
  <div class="flex flex-col items-start justify-around">
    <div class="text-sm leading-5 text-gray-900">
      <div class="mb-1 text-sm leading-5 text-gray-500">Résultat total</div>
      <div
        class="inline-flex items-baseline rounded-full text-sm font-medium leading-5 px-2.5 py-0.5 md:mt-2 lg:mt-0"
        :class="
          result >= 0 ? 'bg-green-500 text-white' : 'bg-red-500 text-white'
        "
      >
        <svg
          class="-ml-1 flex-shrink-0 self-center h-5 w-5 mr-0.5 text-white"
          fill="currentColor"
          viewbox="0 0 20 20"
        >
          <path
            v-if="result >= 0"
            fill-rule="evenodd"
            d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z"
            clip-rule="evenodd"
          ></path>
          <path
            v-if="result &lt; 0"
            fill-rule="evenodd"
            d="M5 10a1 1 0 011-1h8a1 1 0 110 2H6a1 1 0 01-1-1z"
            clip-rule="evenodd"
          ></path></svg
        >{{ Math.abs(result) }} €
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PlayerTotalResult",
  props: {
    result: Number,
  },
};
</script>

<style scoped>
</style>
<template>
  <div
    class="badge mr-1 flex flex-shrink-0 items-baseline rounded-full text-sm font-medium leading-5 transition-all duration-200 px-2.5 py-0.5"
    :class="playerResult.result &gt;= 0 ? 'bg-green-500 text-white' : 'bg-red-500 text-white'"
  >
    <svg
      class="-ml-2 -mr-2 flex-shrink-0 self-center h-5 w-5 mr-0.5"
      fill="currentColor"
      viewbox="0 0 20 20"
      :class="playerResult.result &gt;= 0 ? 'text-green-100' : 'text-red-100'"
    >
      <path
        v-if="playerResult.result &gt;= 0"
        fill-rule="evenodd"
        d="M5.293 9.707a1 1 0 010-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 01-1.414 1.414L11 7.414V15a1 1 0 11-2 0V7.414L6.707 9.707a1 1 0 01-1.414 0z"
        clip-rule="evenodd"
      ></path>
      <path
        v-if="playerResult.result &lt; 0"
        fill-rule="evenodd"
        d="M14.707 10.293a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 111.414-1.414L9 12.586V5a1 1 0 012 0v7.586l2.293-2.293a1 1 0 011.414 0z"
        clip-rule="evenodd"
      ></path>
    </svg>
    <span class="ml-2">{{ Math.abs(playerResult.result) }}&nbsp;&euro;</span>
  </div>
</template>

<script>
export default {
  name: "ResultBadge",
  props: {
    playerResult: Object,
  },
};
</script>

<style scoped>
</style>
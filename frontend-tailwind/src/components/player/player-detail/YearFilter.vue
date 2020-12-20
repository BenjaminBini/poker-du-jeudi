<template>
  <button
    class="relative inline-flex items-center px-4 py-2 text-sm font-medium border border-transparent rounded-md shadow-sm focus:outline-none"
    @click="$emit('year-selected', $event, year)"
    type="button"
    :class="
      selectedYear === year
        ? 'text-white bg-indigo-500'
        : 'bg-transparent border-indigo-500 text-indigo-500 text-gray-500'
    "
  >
    {{ label }}
  </button>
</template>

<script>
export default {
  name: "YearFilter",
  props: ["year", "label", "selectedYear"],
};
</script>

<style scoped>
</style>
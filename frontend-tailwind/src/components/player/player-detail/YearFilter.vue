<template lang="pug">
  button(@click="$emit('year-selected', $event, year)" type="button"
    class="relative inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md focus:outline-none"
    :class="selectedYear === year ? 'text-white bg-indigo-500' : 'bg-transparent border-indigo-500 text-indigo-500 text-gray-500'")
    | {{label}}
</template>

<script>
export default {
  name: "YearFilter",
  props: ["year", "label", "selectedYear"],
}
</script>

<style scoped>

</style>
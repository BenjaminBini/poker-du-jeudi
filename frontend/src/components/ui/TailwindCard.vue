<template>
  <div
    class="overflow-hidden bg-white divide-y divide-gray-200 rounded-lg shadow"
  >
    <h3 class="px-6 py-5 text-xl font-bold leading-6 text-indigo-600">
      {{ title }}
    </h3>
    <div class="px-4 py-5 sm:p-6">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "TailwindCard",
  props: ["title"],
};
</script>
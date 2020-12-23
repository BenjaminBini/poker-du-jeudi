<template>
  <div>
    <router-link
      :to="target"
      v-if="type === 'primary'"
      type="button"
      class="inline-flex items-center font-medium text-white bg-indigo-600 border border-transparent rounded shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
      :class="computedClasses"
    >
      <slot></slot>
    </router-link>
    <router-link
      :to="target"
      v-if="type === 'secondary'"
      type="button"
      class="inline-flex items-center font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
      :class="computedClasses"
    >
      <slot></slot>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "TailwindButton",
  props: ["size", "type", "target"],
  computed: {
    computedClasses: function () {
      switch (this.size) {
        case "xs":
          return "px-2.5 py-1.5 text-xs";
        case "sm":
          return "px-3 py-1.5 text-sm";
        case "md":
          return "px-4 py-2 text-sm";
        case "lg":
          return "px-4 py-2 text-base";
        case "xl":
          return "px-6 py-3 text-base";
        default:
          return "";
      }
    },
  },
};
</script>
<template>
  <div>
    <router-link
      :to="target ? target : '/'"
      v-if="target"
      type="button"
      :class="computedClasses"
    >
      <slot></slot>
    </router-link>
    <button
      :class="computedClasses"
      v-if="!target"
      @click="$emit('buttonClick')"
    >
      <slot></slot>
    </button>
  </div>
</template>

<script>
export default {
  name: "TailwindButton",
  props: ["size", "type", "target"],
  computed: {
    computedClasses: function () {
      let classes = "";
      switch (this.type) {
        case "primary":
          classes +=
            "inline-flex items-center justify-center font-medium text-white bg-indigo-600 border border-transparent rounded-md shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500";
          break;
        case "secondary":
          classes +=
            "inline-flex items-center justify-center font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500";
          break;
        case "alert":
          classes +=
            "inline-flex items-center justify-center  border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500";
          break;
      }
      switch (this.size) {
        case "xs":
          classes += " px-2.5 py-1.5 text-xs";
          break;
        case "sm":
          classes += " px-3 py-1.5 text-sm";
          break;
        case "md":
          classes += " px-4 py-2 text-sm";
          break;
        case "lg":
          classes += " px-4 py-2 text-base";
          break;
        case "xl":
          classes += " px-6 py-3 text-base";
          break;
        default:
          classes += "";
          break;
      }
      return classes;
    },
  },
};
</script>
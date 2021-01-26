<template>
  <div
    class="px-4 mx-auto max-w-7xl sm:px-6 lg:px-8 md:flex md:items-center md:justify-between"
  >
    <div class="flex-1 min-w-0">
      <h2
        class="text-2xl font-bold leading-7 text-white sm:text-3xl sm:truncate"
      >
        {{ pageTitle }}
      </h2>
    </div>
    <div class="flex mt-4 space-x-3 md:mt-0 md:ml-4">
      <tailwind-button
        v-for="(action, i) in pageActions"
        :key="i"
        type="secondary"
        size="sm"
        :target="action.target"
        >{{ action.label }}
      </tailwind-button
      >
    </div>
  </div>
</template>
<script>
import TailwindButton from "@/components/ui/TailwindButton";

export default {
  name: "CustomHeading",
  props: ["pageTitle", "pageActions"],
  components: { TailwindButton },
};
</script>
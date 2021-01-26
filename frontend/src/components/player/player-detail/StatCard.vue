<template>
  <div class="overflow-hidden bg-white rounded-lg shadow">
    <div class="px-4 py-5 sm:p-6">
      <div v-if="loading" class="flex justify-center py-4">
        <tw-spinner></tw-spinner>
      </div>
      <div v-if="!loading" class="flex items-center">
        <div
          class="flex-shrink-0 p-3 rounded-md"
          :class="colorClass ? colorClass : 'bg-indigo-500'"
        >
          <slot></slot>
        </div>
        <div class="flex-1 w-0 ml-5">
          <dt class="text-sm font-medium text-gray-500 truncate">
            {{ label }}
          </dt>
          <dd class="flex items-center space-x-3">
            <div class="text-2xl font-semibold text-gray-900">{{ stat }}</div>
            <result-badge v-if="change" :player-result="change"></result-badge>
            <div
              v-if="neutralBadge"
              class="badge mr-1 flex flex-shrink-0 items-baseline rounded-full text-sm font-medium leading-5 px-2.5 py-0.5 bg-indigo-500 text-white"
            >
              {{ neutralBadge }}
            </div>
          </dd>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "StatCard",
  props: ["label", "stat", "change", "colorClass", "loading", "neutralBadge"],
};
</script>

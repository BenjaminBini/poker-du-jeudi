<template>
  <div class="flex text-sm leading-5 text-gray-500">
    <svg
      class="flex-shrink-0 h-5 w-5 text-gray-400 mr-1.5"
      xmlns="http://www.w3.org/2000/svg"
      viewbox="0 0 20 20"
      fill="currentColor"
    >
      <path
        fill-rule="evenodd"
        d="M6.267 3.455a3.066 3.066 0 001.745-.723 3.066 3.066 0 013.976 0 3.066 3.066 0 001.745.723 3.066 3.066 0 012.812 2.812c.051.643.304 1.254.723 1.745a3.066 3.066 0 010 3.976 3.066 3.066 0 00-.723 1.745 3.066 3.066 0 01-2.812 2.812 3.066 3.066 0 00-1.745.723 3.066 3.066 0 01-3.976 0 3.066 3.066 0 00-1.745-.723 3.066 3.066 0 01-2.812-2.812 3.066 3.066 0 00-.723-1.745 3.066 3.066 0 010-3.976 3.066 3.066 0 00.723-1.745 3.066 3.066 0 012.812-2.812zm7.44 5.252a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
        clip-rule="evenodd"
      ></path>
    </svg>
    <span class="truncate">{{ sessionCount }} participations</span>
  </div>
</template>

<script>
export default {
  name: "SessionCount",
  props: {
    sessionCount: Number,
  },
};
</script>

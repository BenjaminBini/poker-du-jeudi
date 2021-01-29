<script>
import { Bar } from "vue-chartjs";
import settings from "@/settings";
const colors = require("tailwindcss/colors");

const color = function (context) {
  const index = context.dataIndex;
  const playerResult = context.dataset.data[index];
  return playerResult >= 0 ? settings.green : settings.red;
};

const updateChart = function (results, renderFunction) {
  if (!results || results.length === 0) return;
  results.sort((r1, r2) => r2.result - r1.result);
  const labels = results.map((r) => r.player.firstName);
  const resultsData = results.map((r) => r.result);
  const buyInsData = results.map((r) => r.buyIns * 10);
  const chartData = {
    datasets: [
      {
        label: "Buy-ins",
        data: buyInsData,
        backgroundColor: colors.indigo[400],
        barThickness: 15,
        xAxisID: "bi-axes-id",
        datalabels: {
          display: false,
        },
      },
      {
        label: "Résultats",
        data: resultsData,
        backgroundColor: color,
        maxBarThickness: 40,
        xAxisID: "results-axes-id",
        datalabels: {
          color: function (context) {
            const value = context.dataset.data[context.dataIndex];
            if (Math.abs(value) > 10) {
              return "white";
            } else {
              return "#474747";
            }
          },
          rotation: function (context) {
            let currentMeta =
              context.dataset._meta[Object.keys(context.dataset._meta)[0]];
            if (currentMeta) {
              const width = currentMeta.data[context.dataIndex]._model.width;
              if (width < 30) {
                return 90;
              }
            }
            return 0;
          },
          formatter: function (value) {
            return `${value} €`;
          },
          font: {
            weight: "bold",
          },
          anchor: (context) => {
            const value = context.dataset.data[context.dataIndex];
            return value >= 10 ? "end" : "start";
          },
          align: (context) => {
            const value = context.dataset.data[context.dataIndex];
            return value >= -10 ? "start" : "end";
          },
        },
      },
    ],
    labels,
  };
  const stepSize =
    Math.max(
      Math.max(...resultsData, ...buyInsData),
      -Math.min(...resultsData, ...buyInsData)
    ) > 55
      ? 20
      : 10;
  const options = {
    responsive: true,
    maintainAspectRatio: false,
    legend: {
      display: true,
      position: "bottom",
    },
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true,
            max:
              Math.floor((Math.max(...resultsData, ...buyInsData) + 20) / 20) *
              20,
            min:
              Math.ceil((Math.min(...resultsData, ...buyInsData) - 20) / 20) *
              20,
            stepSize,
          },
          stacked: false,
        },
      ],
      xAxes: [
        {
          id: "bi-axes-id",
          stacked: true,
          offset: true,
          gridLines: {
            display: false,
          },
        },
        {
          id: "results-axes-id",
          display: false,
          stacked: true,
          offset: true,
          gridLines: {
            display: false,
          },
        },
      ],
    },
  };
  renderFunction(chartData, options);
};

export default {
  name: "SessionResultChart",
  extends: Bar,
  props: ["results"],
  watch: {
    results: function () {
      updateChart([...this.results], this.renderChart);
    },
  },
  mounted: function () {
    if (Array.isArray(this.results))
      updateChart([...this.results], this.renderChart);
  },
};
</script>
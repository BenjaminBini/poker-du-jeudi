
<script>
import {Bar} from "vue-chartjs";
import ChartDataLabels from 'chartjs-plugin-datalabels';
import SessionService from "@/services/session-service";
import router from "@/router";

export default {
  extends: Bar,
  props: ['results', 'type'],
  data: function() {
    const self = this;
    return  {
      chartData: {
        datasets: [{}],
      },
      chartOptions: {
        maintainAspectRatio: false,
        tooltips: {
          enabled: false,
        },
        legend: {
          display: false,
        },
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true,
            }
          }]
        },
        onClick: function(e) {
          let clickedElement = this.getElementsAtEvent(e);
          console.log(e);
          console.log(this.getDatasetMeta(0));
          if (clickedElement && clickedElement.length > 0) {
            let playerName = this.getElementsAtEvent(e)[0]._model.label;
            let playerId = self.playerIdMap[playerName];
            //router.push({path: `/players/${playerId}`})
          }
        },
        plugins: {
          // Change options for ALL labels of THIS CHART
          datalabels: {
            align: 'bottom',
            anchor: 'end',
            formatter: (value, context) => {
              if (this.type === 'result') {
                return value + ' €';
              }
              return value;
            },
            listeners: {
              enter: function (context) {
                // Receives `enter` events for any labels of any dataset. Indices of the
                // clicked label are: `context.datasetIndex` and `context.dataIndex`.
                // For example, we can modify keep track of the hovered state and
                // return `true` to update the label and re-render the chart.
                console.log(context);
                context.hovered = true;
                return true;
              },
            }
          }
        }
      },
      playerIdMap: {}
    }
  },

  mounted () {
    this.results = this.results.sort((r1, r2) => r2[this.type] - r1[this.type]);
    this.chartData.labels = this.results.map(r => r.player.firstName);
    this.chartData.datasets[0].data = this.results.map(r => r[this.type]);
    this.results.forEach(r => this.playerIdMap[r.player.firstName] = r.player.playerId);
    //this.chartData.datasets[0].hoverBackgroundColor = 'rgba(0, 0, 0, 1)';
    if (this.type === "result") {
      this.chartOptions.plugins.datalabels.rotation = 90;
    }
    if (this.type === "buyIns") {
      this.chartOptions.scales.yAxes[0].ticks.stepSize = 1;
    }
    this.renderChart(this.chartData, this.chartOptions)
  }
}
</script>

<style scoped>

</style>
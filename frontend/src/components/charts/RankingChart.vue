
<script>
import {HorizontalBar} from "vue-chartjs";
import ChartDataLabels from 'chartjs-plugin-datalabels';
import router from "@/router";

export default {
  extends: HorizontalBar,
  props: ['ranking'],
  data: function() {
    const self = this;
    return  {
      chartData: {
        datasets: [{}],
      },
      chartOptions: {
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
          console.log(clickedElement);
          if (clickedElement && clickedElement.length > 0) {
            let playerName = this.getElementsAtEvent(e)[0]._model.label;
            let playerId = self.playerIdMap[playerName];
            router.push({path: `/players/${playerId}`})
          }
        },
        plugins: {
          // Change options for ALL labels of THIS CHART
          datalabels: {
            align: 'left',
            anchor: 'end',
            clamp: true,
            formatter: (value, context) => value + ' €',
          }
        }
      },
      playerIdMap: {}
    }
  },

  mounted () {
    this.chartData.labels = this.ranking.map(r => r.firstName);
    this.chartData.datasets[0].data = this.ranking.map(r => r.result);
    this.renderChart(this.chartData, this.chartOptions)
    this.renderChart(this.chartData, this.chartOptions)
    this.ranking.forEach(r => this.playerIdMap[r.firstName] = r.playerId);
  }
}
</script>

<style scoped>

</style>
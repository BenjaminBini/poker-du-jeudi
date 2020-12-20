import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router';
import App from './App.vue';
import './main.css';
import routes from "./router/routes";
import ResultBadge from "./components/player/players-list/ResultBadge";
import SessionCount from "./components/player/players-list/SessionCount";
import PlayerListItem from "./components/player/players-list/PlayerListItem";
import PlayerPicture from "./components/player/PlayerPicture";
import PlayerLastSession from "./components/player/PlayerLastSession";
import PlayerTotalResult from "./components/player/PlayerTotalResult";

Vue.config.productionTip = false;
Vue.use(Vuex)
Vue.use(VueRouter);

Vue.component('result-badge', ResultBadge);
Vue.component('session-count', SessionCount);
Vue.component('player-list-item', PlayerListItem);
Vue.component('player-picture', PlayerPicture);
Vue.component('player-last-session', PlayerLastSession);
Vue.component('player-total-result', PlayerTotalResult);

const store = new Vuex.Store({
  state: {
    pageTitle: 'Poker du jeudi'
  },
  mutations: {
    setPageTitle (state, title) {
      state.pageTitle = title;
    }
  }
})

const router = new VueRouter({
  routes,
  mode: 'history',
});

router.beforeEach((to, from, next) => {
  if (to.meta.pageTitle) {
    store.commit('setPageTitle', to.meta.pageTitle);
  }
  next();
});

new Vue({
  router: router,
  store: store,
  render: h => h(App),
}).$mount('#app');

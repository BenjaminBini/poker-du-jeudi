import "./main.css";

import App from "./App.vue";
import PlayerLastSession from "./components/player/PlayerLastSession";
import PlayerListItem from "./components/player/players-list/PlayerListItem";
import PlayerPicture from "./components/player/PlayerPicture";
import PlayerTotalResult from "./components/player/PlayerTotalResult";
import ResultBadge from "./components/player/players-list/ResultBadge";
import SessionCount from "./components/player/players-list/SessionCount";
import TailwindSpinner from "@/components/ui/TailwindSpinner";
import ChartDataLabels from "chartjs-plugin-datalabels";
import Vue from "vue";
import VueRouter from "vue-router";
import Vuex from "vuex";
import routes from "./router/routes";
import TailwindModal from "./components/ui/TailwindModal.vue";
import { Axios } from "@/services/axios-service";

Vue.config.productionTip = false;
Vue.use(Vuex);
Vue.use(VueRouter);

Vue.component("result-badge", ResultBadge);
Vue.component("session-count", SessionCount);
Vue.component("player-list-item", PlayerListItem);
Vue.component("player-picture", PlayerPicture);
Vue.component("player-last-session", PlayerLastSession);
Vue.component("player-total-result", PlayerTotalResult);
Vue.component("tw-spinner", TailwindSpinner);
Vue.component("tw-modal", TailwindModal);

const store = new Vuex.Store({
  state: {
    pageTitle: "Poker du jeudi",
    pageActions: [],
    editMode: !!localStorage.getItem("jwt"),
  },
  mutations: {
    enableEditMode(state) {
      state.editMode = true;
    },
    disableEditMode(state) {
      state.editMode = false;
    },
    setPageTitle(state, title) {
      state.pageTitle = title;
      document.title = `${title} - Poker du jeudi`;
    },
    setPageActions(state, actions) {
      state.pageActions = actions;
    },
  },
});

const router = new VueRouter({
  routes,
  mode: "history",
});

router.beforeEach((to, from, next) => {
  if (to.meta.pageTitle) {
    store.commit("setPageTitle", to.meta.pageTitle);
  }
  store.commit("setPageActions", []);
  next();
});

new Vue({
  router: router,
  store: store,
  render: (h) => h(App),
  components: {
    ChartDataLabels,
  },
}).$mount("#app");

Axios.interceptors.request.use(
  function(config) {
    // Do something before request is sent
    const jwt = localStorage.getItem("jwt");
    if (jwt) {
      config.headers.common["Authorization"] = `Bearer ${localStorage.getItem(
        "jwt"
      )}`;
      store.commit("enableEditMode");
    } else {
      store.commit("disableEditMode");
    }
    return config;
  },
  function(error) {
    return Promise.reject(error);
  }
);

Axios.interceptors.response.use(
  function(config) {
    return config;
  },
  function(error) {
    if (error.response.status === 403) {
      localStorage.clear();
      location.reload();
    }
    return Promise.reject(error);
  }
);

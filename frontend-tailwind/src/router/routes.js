import Foo from "../components/sample/Foo";
import Sessions from "../components/Sessions";
import Players from "../components/player/players-list/Players";
import Player from "../components/player/Player";

const routes = [
    {
        path: "/",
        component: Foo,
        meta: {
            label: "Accueil",
            pageTitle: "Poker du jeudi",
            displayInMenu: true
        }
    }, {
        path: "/sessions",
        component: Sessions,
        meta: {
            label: "Sessions",
            displayInMenu: true
        },
    }, {
        path: "/players",
        component: Players,
        meta: {
            label: "Joueurs",
            displayInMenu: true
        },
    }, {
        path: "/players/:id",
        component: Player,
        meta:  {
            displayInMenu: false,
        }
    },
];

export default routes;

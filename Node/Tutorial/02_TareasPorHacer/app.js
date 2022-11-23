require("colors");
const { mostrarMenu, pausa } = require("./helpers/mensajes");

const main = async () => {
  let opt = "";

  do {
    mostrarMenu();
  } while (opt !== "0");
};

main();

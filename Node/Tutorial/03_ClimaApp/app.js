const { leerInput, inquirerMenu, pausa } = require("./helpers/inquirer.js");
const Busquedas = require("./models/busquedas.js");

const main = async () => {
  const busquedas = new Busquedas();
  let opt = "";

  do {
    opt = await inquirerMenu();

    switch (opt) {
      case 1:
        console.log("Buscar");
        /**
         * TODO: Buscar las ciudades y mostrarlos en una lista como el tipo menú.
         */

        //? Mostrar mensaje
        const lugar = await leerInput("Ciudad: ");
        console.log(lugar);

        //? Buscar los lugares

        //? Seleccionar el lugar

        //? Clime

        //? Mostrar resultado (información del clima del lugar seleccionado)
        console.log("\nInformación de la ciudad\n".green);
        console.log("Ciudad:");
        console.log("Lat:");
        console.log("Lng:");
        console.log("Temperatura:");
        console.log("Mínima:");
        console.log("Máxima:");

        break;
      case 2:
        console.log("Historial");
        break;
      default:
        break;
    }
    await pausa();
  } while (opt !== 0);
};

main();

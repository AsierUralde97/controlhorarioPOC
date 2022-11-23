require("colors");

const mostrarMenu = async () => {
  console.clear();

  console.log("==================================".green);
  console.log("   Selecciona una opción".green);
  console.log("==================================".green);

  console.log(`${"1.".green} Crear una tarea`);
  console.log(`${"2.".green} Listar tareas`);
  console.log(`${"3.".green} Listar tareas completadas`);
  console.log(`${"4.".green} Listar tareas pendientes`);
  console.log(`${"5.".green} Completar tarea(s)`);
  console.log(`${"6.".green} Borrar tareas`);
  console.log(`${"0.".green} Salir \n`);

  const readline = require("readline").createInterface({
    input: process.stdin,
    output: process.stdout,
  });

  readline.question("Seleeciona una opción: ", (opt) => {
    readline.close();
  });
};

const pausa = async () => {
  await mostrarMenu;
  const readline = require("readline").createInterface({
    input: process.stdin,
    output: process.stdout,
  });

  readline.question(`\nPresione ${"ENTER".green} para continuar: \n`, (opt) => {
    readline.close();
  });
};
module.exports = {
  mostrarMenu,
  pausa,
};

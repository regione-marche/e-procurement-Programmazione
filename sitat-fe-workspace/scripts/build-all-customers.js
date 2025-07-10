const { execSync } = require("child_process");
const fs = require("fs");
const path = require("path");


const destination_dir="C:\\dist/";


const CUSTOMER_MAP_CONTRATTI = [
  { "key": "aipo", "value": "csi" },
  { "key": "basilicata", "value": "basilicata" },
  { "key": "cineca", "value": "cineca" },
  { "key": "comge", "value": "esecuzione" },
  { "key": "csi", "value": "csi" },
  { "key": "esecuzione", "value": "esecuzione" },
  { "key": "marche", "value": "reg-marche" },
  { "key": "rai", "value": "rai" },
  { "key": "scp-esecuzione", "value": "scp" },
  { "key": "vapt", "value": "vapt" }
];


const CUSTOMER_MAP_PROGRAMMI = [ 
  { "key": "basilicata", "value": "basilicata" },
  { "key": "marche", "value": "reg-marche" },
  { "key": "vapt", "value": "vapt" },
  { "key": "monza", "value": "standard" }
];

const project_contratti = "app-contratti";
const project_programmi = "app-programmi";
const buildSource_contratti = `dist/${project_contratti}`;
const buildSource_programmi = `dist/${project_programmi}`;
const dest_contratti = "contratti-fe";
const dest_programmi = "programmi-fe";

console.log("\n🔨 Starting build for all customers...\n");

try {
  // 1️⃣ Esegui la build dei contratti UNA SOLA VOLTA
  execSync(`yarn run build:${project_contratti}`, { stdio: "inherit" });
  console.log(`✅ Build completed for ${project_contratti}\n`);
} catch (error) {
  console.error(`❌ Error during build`, error);
  process.exit(1);
}

try {
  // 2️⃣ Esegui la build dei programmi UNA SOLA VOLTA
  execSync(`yarn run build:${project_programmi}`, { stdio: "inherit" });
  console.log(`✅ Build completed for ${project_programmi}\n`);
} catch (error) {
  console.error(`❌ Error during build`, error);
  process.exit(1);
}

// 3️⃣ Copia la build dei contratti nella cartella di ogni customer
for (const customer of CUSTOMER_MAP_CONTRATTI) {
  const customerKey = customer.key;
  const customerValue = customer.value;
  const customerDist = `${destination_dir}${customerKey}`;

  console.log(`\n📦 Copying build files for customer: ${customerKey}...`);

  try {
    // Crea la cartella del customer se non esiste
    if (!fs.existsSync(customerDist)) {
      fs.mkdirSync(customerDist, { recursive: true });
    }

    // Copia tutti i file della build nella cartella del customer
    fs.readdirSync(buildSource_contratti).forEach(file => {
      fs.cpSync(`${buildSource_contratti}/${file}`, `${customerDist}/${dest_contratti}/${file}`, { recursive: true });
    });

    console.log(`✅ Build copied to ${customerDist}`);
  } catch (error) {
    console.error(`❌ Error copying build files for ${customer}`, error);
    process.exit(1);
  }
}

// 4️⃣ Copia la build dei programmi nella cartella di ogni customer
for (const customer of CUSTOMER_MAP_PROGRAMMI) {
  const customerKey = customer.key;
  const customerValue = customer.value;
  const customerDist = `${destination_dir}${customerKey}`;
  console.log(`\n📦 Copying build files for customer: ${customerKey}...`);

  try {
    // Crea la cartella del customer se non esiste
    if (!fs.existsSync(customerDist)) {
      fs.mkdirSync(customerDist, { recursive: true });
    }

    // Copia tutti i file della build nella cartella del customer
    fs.readdirSync(buildSource_programmi).forEach(file => {
      fs.cpSync(`${buildSource_programmi}/${file}`, `${customerDist}/${dest_programmi}/${file}`, { recursive: true });
    });

    console.log(`✅ Build copied to ${customerDist}`);
  } catch (error) {
    console.error(`❌ Error copying build files for ${customerKey}`, error);
    process.exit(1);
  }
}

// 5️⃣ Esegui gli assets per i contratti per ogni customer
for (const customer of CUSTOMER_MAP_CONTRATTI) {
  const customerKey = customer.key;
  const customerValue = customer.value;
  const customerDist = `${destination_dir}${customerKey}`;
  execSync(`rm -rf dist/app-contratti`, { stdio: "inherit" });

  console.log(`\n🎨 Generating assets for customer: ${customerKey}...`);
  try {
    // Genera gli assets per ogni customer dei contratti
    execSync(`yarn run preset:customer --project app-contratti --customer ${customerValue} --projectType sa --environmentType production && yarn run postcss --project app-contratti && yarn run compile:assets --project app-contratti && yarn run minify:json --project app-contratti`, {
      stdio: "inherit"
    });
    console.log(`✅ Assets generated for ${customerKey}\n`);
  } catch (error) {
    console.error(`❌ Error generating assets for ${customerKey}`, error);
    process.exit(1);
  }

  // 6️⃣ Copia gli assets nella cartella di ogni customer per i contratti
  console.log(`\n📂 Copying assets to customer: ${customerKey}...`);
  try {
    const assetsSource = `dist/app-contratti`;
    fs.readdirSync(assetsSource).forEach(file => {
      fs.cpSync(`${assetsSource}/${file}`, `${customerDist}/${dest_contratti}/${file}`, { recursive: true });
    });

    console.log(`✅ Assets copied to ${customerDist}`);
  } catch (error) {
    console.error(`❌ Error copying assets for ${customerKey}`, error);
    process.exit(1);
  }
}

console.log("\n✅ Contratti builds and assets processing completed!");

// 7️⃣ Esegui gli assets per i programmi per ogni customer
for (const customer of CUSTOMER_MAP_PROGRAMMI) {
  const customerKey = customer.key;
  const customerValue = customer.value;
  const customerDist = `${destination_dir}${customerKey}`;
  execSync(`rm -rf dist/app-programmi`, { stdio: "inherit" });

  console.log(`\n🎨 Generating assets for customer: ${customerKey}...`);
  try {
    // Genera gli assets per ogni customer dei programmi
    execSync(`yarn run preset:customer --project app-programmi --customer ${customerValue} --projectType sa --environmentType production && yarn run postcss --project app-programmi && yarn run compile:assets --project app-programmi && yarn run minify:json --project app-programmi`, {
      stdio: "inherit"
    });
    console.log(`✅ Assets generated for ${customerKey}\n`);
  } catch (error) {
    console.error(`❌ Error generating assets for ${customerKey}`, error);
    process.exit(1);
  }

  // 8️⃣ Copia gli assets nella cartella di ogni customer per i programmi
  console.log(`\n📂 Copying assets to customer: ${customerKey}...`);
  try {
    const assetsSource = `dist/app-programmi`;
    fs.readdirSync(assetsSource).forEach(file => {
      fs.cpSync(`${assetsSource}/${file}`, `${customerDist}/${dest_programmi}/${file}`, { recursive: true });
    });

    console.log(`✅ Assets copied to ${customerDist}`);
  } catch (error) {
    console.error(`❌ Error copying assets for ${customerKey}`, error);
    process.exit(1);
  }
}

console.log("\n✅ Programmi builds and assets processing completed!");

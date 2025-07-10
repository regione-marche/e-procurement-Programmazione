const { execSync } = require("child_process");
const fs = require("fs");
const path = require("path");

const CUSTOMER_MAP = [
  { "key": "aipo", "value": "aipo-ese" },
  { "key": "basilicata", "value": "basilicata" },
  { "key": "cineca", "value": "cineca" },
  { "key": "comge", "value": "comge" },
  { "key": "csi", "value": "csi" },
  { "key": "esecuzione", "value": "esecuzione" },
  { "key": "marche", "value": "reg-marche" },
  { "key": "rai", "value": "rai" },
  { "key": "scp-esecuzione", "value": "scp" },
  { "key": "vapt", "value": "vapt" },
  { "key": "monza", "value": "monza" }
];


const destination_dir="C:\\dist/";

const project_launcher = "app-launcher";
const buildSource_launcher = `dist/${project_launcher}`;
const dest_launcher = "launcher-fe";

console.log("\n🔨 Starting build for all customers...\n");

try {
  // 1️⃣ Esegui la build del launcher UNA SOLA VOLTA
  execSync(`yarn run build`, { stdio: "inherit" });
  console.log(`✅ Build completed for ${project_launcher}\n`);
} catch (error) {
  console.error(`❌ Error during build`, error);
  process.exit(1);
}



// 3️⃣ Copia la build del launcher nella cartella di ogni customer
for (const customer of CUSTOMER_MAP) {
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
    fs.readdirSync(buildSource_launcher).forEach(file => {
      fs.cpSync(`${buildSource_launcher}/${file}`, `${customerDist}/${dest_launcher}/${file}`, { recursive: true });
    });

    console.log(`✅ Build copied to ${customerDist}`);
  } catch (error) {
    console.error(`❌ Error copying build files for ${customer}`, error);
    process.exit(1);
  }
}


// 5️⃣ Esegui gli assets per launcher per ogni customer
for (const customer of CUSTOMER_MAP) {
  const customerKey = customer.key;
  const customerValue = customer.value;
  const customerDist = `${destination_dir}${customerKey}`;
  execSync(`rm -rf dist/app-launcher`, { stdio: "inherit" });

  console.log(`\n🎨 Generating assets for customer: ${customerKey}...`);
  try {
    // Genera gli assets per ogni customer del launcher
    
    execSync(`yarn run preset:customer --project app-launcher --customer ${customerValue} --projectType sa --environmentType production && yarn run postcss --project app-launcher && yarn run compile:assets --project app-launcher && yarn run minify:json --project app-launcher`, {
      stdio: "inherit"
    });
    console.log(`✅ Assets generated for ${customerKey}\n`);
  } catch (error) {
    console.error(`❌ Error generating assets for ${customerKey}`, error);
    process.exit(1);
  }

  // 6️⃣ Copia gli assets nella cartella di ogni customer per launcher
  console.log(`\n📂 Copying assets to customer: ${customerKey}...`);
  try {
    const assetsSource = `dist/app-launcher`;
    fs.readdirSync(assetsSource).forEach(file => {
      fs.cpSync(`${assetsSource}/${file}`, `${customerDist}/${dest_launcher}/${file}`, { recursive: true });
    });

    console.log(`✅ Assets copied to ${customerDist}`);
  } catch (error) {
    console.error(`❌ Error copying assets for ${customerKey}`, error);
    process.exit(1);
  }
}

console.log("\n✅ Contratti builds and assets processing completed!");